package com.gscf.wallpaper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    private static final String SIZE_SEPARATOR = "x";
    private static final String DEFAULT_FILE_PATH = "/";

    private static int totalSquare = 0;
    private static final Map<String, WallpaperRoomEntity> uniqRooms = new HashMap<>();
    private static final List<String> duplicatedRooms = new ArrayList<>();
    private static final Set<WallpaperRoomEntity> cubeRoom = new TreeSet<>();

    public static void main(String[] args) {
        String filePath = DEFAULT_FILE_PATH;
        if (args.length > 0) {
            filePath = args[0];
        }
        try {
            parseFile(filePath);
        } catch (FileNotFoundException e) {
            String errorMessage = "File not found by path: " + filePath;
            logger.error(errorMessage);
            throw new WallpaperSquareServiceException(errorMessage);
        }
        logger.info("Duplicated rooms:");
        duplicatedRooms.forEach(System.out::println);
        logger.info("Cube rooms:");
        cubeRoom.forEach(System.out::println);
        logger.info("Total square: {}", totalSquare);
    }

    private static void parseFile(String filename) throws FileNotFoundException {
        File input = new File(filename);
        Scanner reader = new Scanner(input);
        while (reader.hasNextLine()) {
            String room = reader.nextLine();
            processRoom(room);
        }
        reader.close();
    }

    private static void processRoom(String room) {
        if (uniqRooms.containsKey(room)) {
            duplicatedRooms.add(room);
            WallpaperRoomEntity roomEntity = uniqRooms.get(room);
            totalSquare += roomEntity.getSquare();
        } else {
            String[] sizes = room.split(SIZE_SEPARATOR);
            if (sizes.length != 3) {
                String errorMessage = "List of rooms contains invalid room format: " + room;
                logger.error(errorMessage);
                throw new WallpaperSquareServiceException(errorMessage);
            }
            int length = Integer.parseInt(sizes[0]);
            int width = Integer.parseInt(sizes[1]);
            int height = Integer.parseInt(sizes[2]);
            int extraSquare = getExtraSquare(length, width, height);
            int square = calculateSquare(length, width, height, extraSquare);
            WallpaperRoomEntity roomEntity = new WallpaperRoomEntity(room, square);
            uniqRooms.put(room, roomEntity);
            checkCubeRoom(length, width, height, roomEntity);
            totalSquare += square;
        }
    }

    private static void checkCubeRoom(int length, int width, int height, WallpaperRoomEntity roomEntity) {
        if (length == width && length == height) {
            cubeRoom.add(roomEntity);
        }
    }

    private static int getExtraSquare(int length, int width, int height) {
        int[] digitSizes = new int[]{length, width, height};
        Arrays.sort(digitSizes);
        return digitSizes[0] * digitSizes[1];
    }

    private static int calculateSquare(int length, int width, int height, int extraSquare) {
        return 2 * length * width + 2 * width * height + 2 * height * length + extraSquare;
    }
}
