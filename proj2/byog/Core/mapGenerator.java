package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class mapGenerator { // this class builds a map from a SEED
    private static long SEED;
    private static final int minNumRoom = 100;
    private static final int maxNumRoom = 200;

    public mapGenerator(long SEED) {
        this.SEED = SEED;
    }

    public static TETile[][] matrix(int WIDTH, int HEIGHT) {
        // generate the empty world map
        TETile[][] world = emptyWorld(WIDTH, HEIGHT);

        // setup the queues of openDoors and allDoors
        Queue<Door> openDoors = new LinkedList<>();
        Queue<Door> allDoors = new LinkedList<>();

        // decides how many things to generate from the pseudo random number
        Random RANDOM = new Random(SEED);
        int numRooms = minNumRoom + RANDOM.nextInt(maxNumRoom-minNumRoom);

        // generate the first door. This is the position where the map generation starts, and also the point where the player will be
        Position firstDoorPos = new Position(WIDTH/3 + RANDOM.nextInt(WIDTH/3), HEIGHT/3 + RANDOM.nextInt(HEIGHT/3));
        int firstDoorDir = RANDOM.nextInt(4);
        Door firstDoor = new Door(firstDoorPos,firstDoorDir);
        openDoors.add(firstDoor);
        allDoors.add(firstDoor);

        // generate the first room; post it to the map
        Room firstRoom = Room.makeRoom(world, firstDoor, RANDOM);
        doorQueueMaintainer(world, firstRoom, firstDoor, allDoors, openDoors);

        // generate the rest of the rooms, till numRooms is reached or openDoors queue is empty (means no open doors can be used as seed.
        // door is closed (moved out of openDoors queue) when either already used as a seed, or made maxTrial times of trial and did not successfully generate a valid room
        int i = 0;
        while (i < numRooms && !openDoors.isEmpty()) {
            int roomFlag = RANDOM.nextInt(3);
            // flag >= 1: make an regular random-sized room; flag < 1: make a hallway. The bound of RAMDOM can be changed to tune the rate of room:hallway
            Door queueDoor = openDoors.poll();
            Door seedDoor = Door.mirrorDoor(queueDoor);
            if (roomFlag >= 1) { // make a regular room
                Room newRoom = Room.makeRoom(world, seedDoor, RANDOM);
                if (newRoom.valid) {
                    doorQueueMaintainer(world, newRoom, seedDoor, allDoors, openDoors);
                    i++; // update the number of the generated rooms
                } else {
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }
            } else { // make a hallway
                Hallway newHallway = Hallway.makeHallway(world, seedDoor, RANDOM);
                if (newHallway.valid) {
                    doorQueueMaintainer(world, newHallway, seedDoor, allDoors, openDoors);
                    i++; // update the number of the generated rooms
                } else {
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }
            }
        }
        closeDoor(world, openDoors); // close all remaining doors, if number of trials are exhausted
        openDoor(world,allDoors); // open a door on the wall if the wall of another room happens to block a door
        world[firstDoorPos.xPos][firstDoorPos.yPos] = Tileset.PLAYER; // set player position
        Door.makeDoor(world, RANDOM); // generates an exit

        // return the world map
        return world;
    }

    // generate the empty world map
    private static TETile[][] emptyWorld(int WIDTH, int HEIGHT) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    // push the doors of a room into the queues, and post the room on the world map
    private static void doorQueueMaintainer(TETile[][] world, Room newRoom, Door seedDoor, Queue<Door> allDoors, Queue<Door> openDoors) {
        newRoom.postRoom(world);
        for (Door door : newRoom.roomDoors) { // update the door list
            allDoors.add(door);
            if (!door.equal(seedDoor)) {
                openDoors.add(door);
            }
        }
    }

    // close all remaining doors, if number of trials are exhausted
    private static void closeDoor(TETile[][] map, Queue<Door> openDoors) {
        Door close = openDoors.poll();
        while(close != null) {
            map[close.doorPos.xPos][close.doorPos.yPos] = Tileset.WALL;
            close = openDoors.poll();
        }
    }

    // // open a door on the wall if the wall of another room happens to block a door
    private static void openDoor(TETile[][] map, Queue<Door> allDoors) {
        Door thisDoor = allDoors.poll();
        while (thisDoor != null) {
            if (!map[thisDoor.doorPos.xPos][thisDoor.doorPos.yPos].equals(Tileset.WALL)) {
                // if this door is still an open door, i.e. was not closed during the closeDoor operation, then open its contrast door
                Door mirrorDoor = Door.mirrorDoor(thisDoor);
                if (map[mirrorDoor.doorPos.xPos][mirrorDoor.doorPos.yPos].equals(Tileset.WALL)) {
                    map[mirrorDoor.doorPos.xPos][mirrorDoor.doorPos.yPos] = Tileset.FLOOR;
                }
            }
            thisDoor = allDoors.poll();
        }
    }



/*
    public static TETile[][] world(int WIDTH, int HEIGHT) { // this method generates the TETile matrix of the dimension WIDTH x HEIGHT

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // setup the queue of openDoors, and throw the doors or first room into the queue
        Queue<Door> openDoors = new LinkedList<>();
        Queue<Door> allDoors = new LinkedList<>();

        // decides how many things to generate from the pseudo random number
        Random RANDOM = new Random(SEED);
        int numRooms = minNumRoom + RANDOM.nextInt(maxNumRoom-minNumRoom); // at least 20 rooms, at most 30 rooms

        // generate the first door
        Position firstDoorPos = new Position(WIDTH/3 + RANDOM.nextInt(WIDTH/3), HEIGHT/3 + RANDOM.nextInt(HEIGHT/3));
        int firstDoorDir = RANDOM.nextInt(4);
        Door firstDoor = new Door(firstDoorPos,firstDoorDir);
        openDoors.add(firstDoor);
        allDoors.add(firstDoor);
        Room firstRoom = makeRoom(world, firstDoor, RANDOM);
        if (firstRoom.valid) {
            firstRoom.postRoom(world);
            for (Door door : firstRoom.roomDoors) { // update the door list
                allDoors.add(door);
                if (!door.equal(firstDoor)) {
                    openDoors.add(door);
                }
            }
        }

        /*
        // generate the first room
        Room first = new Room(new Position(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT)), new Size(3 + RANDOM.nextInt(7),3 + RANDOM.nextInt(7)), 2 + RANDOM.nextInt(2), RANDOM);
        // each room has max length of 10, and max doors of 4. repeat until the first valid room is generated
        while(!first.valid(world)) {
            first = new Room(new Position(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT)), new Size(3 + RANDOM.nextInt(7),3 + RANDOM.nextInt(7)), 2 + RANDOM.nextInt(2), RANDOM);
        } */


        /*
        for (Door door : first.roomDoors) {
            openDoors.add(door);
        } */


        // generate the next rooms, until reaching numRoom. Each room is generated based on a door in the openDoors set.
        /* int i = 0;
        while (i < numRooms && !openDoors.isEmpty()) {
            int roomFlag = RANDOM.nextInt(2); // flag >= 1: make an regular random-sized room; flag < 1: make a hallway. The bound of RAMDOM can be changed to tune the rate of room:hallway
            if (roomFlag >= 1) { // make a regular room
                Door queueDoor = openDoors.poll();
                Door newDoor = mirrorDoor(queueDoor);
                Room newRoom = makeRoom(world, newDoor, RANDOM);
                if (newRoom.valid) {
                    newRoom.postRoom(world);
                    for (Door door : newRoom.roomDoors) { // update the door list
                        allDoors.add(door);
                        if (!door.equal(newDoor)) {
                            openDoors.add(door);
                        }
                    }
                    i++;
                } else { // cannot generate room from the door. close the queue door.
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }

            } else { // make a hallway
                Door queueDoor = openDoors.poll();
                Door newDoor = mirrorDoor(queueDoor);
                Hallway newHallway = makeHallway(world, newDoor, RANDOM);
                if (newHallway.valid) {
                    newHallway.postRoom(world);
                    for (Door door : newHallway.roomDoors) { // update the door list
                        allDoors.add(door);
                        if (!door.equal(newDoor)) {
                            openDoors.add(door);
                        }
                    }
                    i++;
                } else { // cannot generate room from the door. close the queue door.
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }
            }
            System.out.println(i);
            /*} else {// make 10 new doors
                Door[] newDoors = generateDoor(world,10,RANDOM);
                for (Door d : newDoors) {
                    openDoors.add(d);
                    allDoors.add(d);
                }
            }*/
        /*}

        // all the rooms are generated; close all boundary doors
        closeDoor(world, openDoors);
        openDoor(world,allDoors);
        world[firstDoorPos.xPos][firstDoorPos.yPos] = Tileset.PLAYER;

        // return the world map
        return world;
    }*/


}
