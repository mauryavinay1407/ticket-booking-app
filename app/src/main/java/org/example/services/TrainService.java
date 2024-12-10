package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    public List<Train> trainList;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAIN_DB_PATH = "app/src/main/java/org/example/localDb/trains.json";

    public TrainService() throws IOException{
        File trains = new File(TRAIN_DB_PATH);
        if (!trains.exists()) {
            throw new IOException("Train database file not found!");
        }
        try {
            trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
        } catch (IOException e) {
            System.err.println("Error reading train data: " + e.getMessage());
            e.printStackTrace();
            throw e; //
        }
    }

    public List<Train> searchTrains(String source, String destination) {
        System.out.println("Searching for trains from: " + source + " to: " + destination);
        List<Train> matchingTrains = trainList.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toList());

        System.out.println("Number of matching trains found: " + matchingTrains.size());
        return matchingTrains;
    }

    public void addTrain(Train newTrain) {
        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
                .findFirst();

        if (existingTrain.isPresent()) {
            updateTrain(newTrain);
        } else {
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain) {
        // Find the index of the train with the same trainId
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();

        if (index.isPresent()) {
            // If found, replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        } else {
            // If not found, treat it as adding a new train
            addTrain(updatedTrain);
        }
    }

    private void saveTrainListToFile() {
        try {
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();

        // Case-insensitive comparison
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}
