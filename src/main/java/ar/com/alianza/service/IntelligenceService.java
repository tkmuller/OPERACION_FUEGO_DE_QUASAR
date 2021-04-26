package ar.com.alianza.service;

import ar.com.alianza.contracts.request.IncomingMessage;
import ar.com.alianza.contracts.request.Position;
import ar.com.alianza.contracts.request.Satellites;
import ar.com.alianza.contracts.response.DecodedMessage;
import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.DecodingMessageException;
import ar.com.alianza.exception.SatelliteAlreadyExistException;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntelligenceService {

    private final SatelliteService satelliteService;

    private final Map<String, IncomingMessage> satelliteMap = new HashMap<>();

    public IntelligenceService(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    public DecodedMessage decodeMessage(List<Satellites> satellites) {

        String[][] messageList = new String[satellites.size()][];

        int satelliteSize = satellites.size();
        double[] distance = new double[satelliteSize];
        double[][] positions = new double[satelliteSize][];

        for (int i = 0; i < satelliteSize; i++) {

            Satellite satellite = satelliteService.findSatelliteByName(satellites.get(i).getName());

            messageList[i] = satellites.get(i).getMessage();
            distance[i] = satellites.get(i).getDistance();
            positions[i] = new double[]{satellite.getX(), satellite.getY()};
        }

        return DecodedMessage.builder()
                .position(getLocation(positions, distance))
                .message(getMessage(messageList))
                .build();
    }

    public boolean addEncodedMessage(String satelliteName, IncomingMessage incomingMessage) {

        if (satelliteMap.containsKey(satelliteName))
            throw new SatelliteAlreadyExistException(satelliteName);

        satelliteService.findSatelliteByName(satelliteName);

        satelliteMap.put(satelliteName, incomingMessage);

        return true;

    }

    public DecodedMessage getDecodeMessage() {

        if (satelliteMap.size() < 3)
            throw new DecodingMessageException(satelliteMap.size());

        double[] distance = new double[satelliteMap.size()];
        double[][] positions = new double[satelliteMap.size()][];
        String[][] messageList = new String[satelliteMap.size()][];

        int i = 0;
        for (Map.Entry<String, IncomingMessage> entry : satelliteMap.entrySet()) {

            Satellite satellite = satelliteService.findSatelliteByName(entry.getKey());
            messageList[i] = entry.getValue().getMessage();
            distance[i] = entry.getValue().getDistance();
            positions[i] = new double[]{satellite.getX(), satellite.getY()};
            i++;
        }

        satelliteMap.clear();
        return DecodedMessage.builder()
                .position(getLocation(positions, distance))
                .message(getMessage(messageList))
                .build();
    }


    private Position getLocation(double[][] positions, double[] distances) {

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        double[] centroid = optimum.getPoint().toArray();

        return Position.builder()
                .x((double) Math.round(centroid[0] * 100d) / 100d)
                .y((double) Math.round(centroid[1] * 100d) / 100d)
                .build();
    }

    private String getMessage(String[][] messages) {

        String[] result = new String[getMaxArraySize(messages)];

        for (String[] message : messages) {
            for (int j = 0; j < message.length; j++) {
                if (message[j].length() != 0) {
                    result[j] = message[j];
                }
            }
        }
        return String.join(" ", result);
    }

    private int getMaxArraySize(String[][] strings) {

        int maxArraySize = 0;
        for (String[] string : strings) {
            if (string.length > maxArraySize)
                maxArraySize = string.length;
        }
        return maxArraySize;
    }
}