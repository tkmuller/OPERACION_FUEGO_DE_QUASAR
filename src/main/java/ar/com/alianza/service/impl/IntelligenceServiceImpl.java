package ar.com.alianza.service.impl;

import ar.com.alianza.contract.request.IncomingMessage;
import ar.com.alianza.contract.request.Position;
import ar.com.alianza.contract.request.Satellites;
import ar.com.alianza.contract.response.DecodedMessage;
import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.DecodingMessageException;
import ar.com.alianza.service.IntelligenceService;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {

    private final SatelliteServiceImpl satelliteServiceImpl;

    private final Map<String, IncomingMessage> signalMap = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(IntelligenceServiceImpl.class);

    public IntelligenceServiceImpl(SatelliteServiceImpl satelliteServiceImpl) {
        this.satelliteServiceImpl = satelliteServiceImpl;
    }

    @Override
    public DecodedMessage decodeMessage(List<Satellites> satellites) {

        String[][] messageList = new String[satellites.size()][];

        int satelliteSize = satellites.size();
        double[] distance = new double[satelliteSize];
        double[][] positions = new double[satelliteSize][];

        for (int i = 0; i < satelliteSize; i++) {

            Satellite satellite = satelliteServiceImpl.findSatelliteByName(satellites.get(i).getName());

            messageList[i] = satellites.get(i).getMessage();
            distance[i] = satellites.get(i).getDistance();
            positions[i] = new double[]{satellite.getX(), satellite.getY()};
        }

        return DecodedMessage.builder()
                .position(getLocation(positions, distance))
                .message(getMessage(messageList))
                .build();
    }

    @Override
    public boolean addEncodedMessage(String satelliteName, IncomingMessage incomingMessage) {

        satelliteServiceImpl.findSatelliteByName(satelliteName);

        signalMap.put(satelliteName, incomingMessage);

        return true;

    }

    @Override
    public DecodedMessage getDecodeMessage() {

        if (signalMap.size() < 3) {
            logger.error("You need at least ( 3 ) signal, you only have ( " + signalMap.size() + " )");
            throw new DecodingMessageException(signalMap.size());
        }

        double[] distance = new double[signalMap.size()];
        double[][] positions = new double[signalMap.size()][];
        String[][] messageList = new String[signalMap.size()][];

        int i = 0;
        for (Map.Entry<String, IncomingMessage> entry : signalMap.entrySet()) {

            Satellite satellite = satelliteServiceImpl.findSatelliteByName(entry.getKey());
            messageList[i] = entry.getValue().getMessage();
            distance[i] = entry.getValue().getDistance();
            positions[i] = new double[]{satellite.getX(), satellite.getY()};
            i++;
        }

        signalMap.clear();
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