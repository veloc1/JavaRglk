package me.veloc1.rglk.game.map.mapgeneration;

import me.veloc1.rglk.game.map.Tile;

import java.util.ArrayList;
import java.util.Random;

public class DungeonGenerator implements MapGenerator {
    @Override
    public Tile[][] generate() {
        int width      = 48;
        int height     = 48;
        int numOfRooms = 18;

        int minRoomWidth  = 6;
        int minRoomHeight = 6;

        Tile[][] result = new Tile[width][height];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = new Tile();
                result[i][j].x = j;
                result[i][j].y = i;
                result[i][j].character = '.';
            }
        }

        Room[] rooms = new Room[numOfRooms];

        int    createdRooms = 0;
        Random random       = new Random();
        int    attempts     = 0;
        while (createdRooms < numOfRooms && attempts < 10) {
            Room newRoom = new Room();
            newRoom.x = random.nextInt(width - 2) + 1;
            newRoom.y = random.nextInt(height - 2) + 1;
            newRoom.width = random.nextInt(width / numOfRooms + minRoomWidth) + minRoomWidth;
            newRoom.height = random.nextInt(height / numOfRooms + minRoomHeight) + minRoomHeight;

            int     xMoves  = 0;
            int     yMoves  = 0;
            boolean isError = false;

            while (isIntersects(newRoom, rooms)) {
                newRoom.x++;
                xMoves++;
                if (xMoves > 10) {
                    xMoves = 0;
                    newRoom.y++;
                    yMoves++;
                }
                if (xMoves > 10 && yMoves > 10) {
                    isError = true;
                    break;
                }
            }

            if (newRoom.x >= width - minRoomWidth || newRoom.y >= height - minRoomHeight) {
                isError = true;
            }
            if (!isError) {
                if (newRoom.x + newRoom.width >= width) {
                    newRoom.width = width - newRoom.x - 1;
                }
                if (newRoom.y + newRoom.height >= height) {
                    newRoom.height = height - newRoom.y - 1;
                }

                if (createdRooms == 0) {
                    result[newRoom.x + 1][newRoom.y + 1].isStartPosition = true;
                }
                rooms[createdRooms] = newRoom;
                createdRooms++;
            } else {
                attempts++;
            }
            /*boolean isError = false;
            for (int oldI = 0; oldI < createdRooms; oldI++) {
                while (newRoom.intersects(rooms[oldI]) && !isError) {
                    newRoom.x++;
                    newRoom.y++;
                    if (newRoom.x >= width - minRoomWidth || newRoom.y >= height - minRoomHeight) {
                        isError = true;
                        break;
                    }
                }
            }
            if (!isError) {
                if (newRoom.x + newRoom.width >= width) {
                    newRoom.width = width - newRoom.x - 1;
                }
                if (newRoom.y + newRoom.height >= height) {
                    newRoom.height = height - newRoom.y - 1;
                }

                if (createdRooms == 0) {
                    result[newRoom.x + 1][newRoom.y + 1].isStartPosition = true;
                }
                rooms[createdRooms] = newRoom;
                createdRooms++;
            } else {
                attempts++;
            }*/
        }

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] == null) {
                continue;
            }
            for (int x = rooms[i].x; x <= rooms[i].x + rooms[i].width; x++) {
                for (int y = rooms[i].y; y <= rooms[i].y + rooms[i].height; y++) {
                    result[x][rooms[i].y].character = '#';
                    result[x][rooms[i].y].isBlock = true;

                    result[x][rooms[i].y + rooms[i].height].character = '#';
                    result[x][rooms[i].y + rooms[i].height].isBlock = true;

                    result[rooms[i].x][y].character = '#';
                    result[rooms[i].x][y].isBlock = true;

                    result[rooms[i].x + rooms[i].width][y].character = '#';
                    result[rooms[i].x + rooms[i].width][y].isBlock = true;
                }
            }
        }

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null) {
                connectRoom(rooms[i], i, rooms, 0, result);
            }
        }

        return result;
    }

    private boolean isIntersects(Room newRoom, Room[] rooms) {
        boolean isIntersects = false;

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] == null) {
                break;
            }
            if (rooms[i].equals(newRoom)) {
                continue;
            }

            if (newRoom.intersects(rooms[i])) {
                isIntersects = true;
            }
        }

        return isIntersects;
    }

    private void connectRoom(Room room, int roomIndex, Room[] rooms, int minDistance, Tile[][] result) {
        for (int i = 0; i < rooms.length; i++) {
            boolean isAllRoomsConnected = checkRoomsIsConnected(rooms);
            int     nearestIndex        = findNearest(room, rooms, minDistance, isAllRoomsConnected);
            if (nearestIndex != -1) {
                Room nearest = rooms[nearestIndex];

                if (nearest != null) {
                    if (!nearest.connected.contains(roomIndex)) {
                        try {
                            createWay(room, nearest, result);
                            room.isConnected = true;
                            room.connected.add(nearestIndex);
                            rooms[nearestIndex].connected.add(roomIndex);
                        } catch (ConnectionException e) {
                            e.printStackTrace();
                        }
                    } else if (minDistance == 0) {
                        connectRoom(room, roomIndex, rooms, room.distanceTo(nearest), result);
                    }
                }
            }
        }
    }

    private void createWay(Room room, Room nearest, Tile[][] result) throws ConnectionException {
        boolean isConnected = false;

        // to left of
        if (nearest.x + nearest.width < room.x) {
            if (nearest.y + nearest.height > room.y && nearest.y < room.y + room.height) {
                int y = Math.max(nearest.y, room.y) + 1;
                if (!(y == nearest.y + nearest.height || y == room.y + room.height)) {
                    for (int i = nearest.x + nearest.width; i <= room.x; i++) {
                        placeTunnel(i, y, true, result);
                    }
                    isConnected = true;
                }
            }
        }

        // to right of
        if (nearest.x > room.x + room.width) {
            if (nearest.y + nearest.height > room.y && nearest.y < room.y + room.height) {
                int y = Math.max(nearest.y, room.y) + 1;
                if (!(y == nearest.y + nearest.height || y == room.y + room.height)) {
                    for (int i = room.x + room.width; i <= nearest.x; i++) {
                        placeTunnel(i, y, true, result);
                    }
                    isConnected = true;
                }
            }
        }

        // above
        if (nearest.y + nearest.height < room.y) {
            if (nearest.x + nearest.width > room.x && nearest.x < room.x + room.width) {
                int x = Math.max(nearest.x, room.x) + 1;
                if (!(x == nearest.x + nearest.width || x == room.x + room.width)) {
                    for (int i = nearest.y + nearest.height; i <= room.y; i++) {
                        placeTunnel(x, i, false, result);
                    }
                    isConnected = true;
                }
            }
        }

        // below
        if (nearest.y > room.y + room.height) {
            if (nearest.x + nearest.width > room.x && nearest.x < room.x + room.width) {
                int x = Math.max(nearest.x, room.x) + 1;
                if (!(x == nearest.x + nearest.width || x == room.x + room.width)) {
                    for (int i = nearest.y + nearest.height; i <= room.y; i++) {
                        placeTunnel(x, i, false, result);
                    }
                    isConnected = true;
                }
            }
        }
        if (!isConnected) {
            throw new ConnectionException();
        }
    }

    private void placeTunnel(int x, int y, boolean isHorizontal, Tile[][] result) {
        result[x][y].isBlock = false;
        result[x][y].character = '!';
        if (isHorizontal) {
            result[x][y - 1].isBlock = true;
            result[x][y - 1].character = '#';
            result[x][y + 1].isBlock = true;
            result[x][y + 1].character = '#';
        } else {
            result[x - 1][y].isBlock = true;
            result[x - 1][y].character = '#';
            result[x + 1][y].isBlock = true;
            result[x + 1][y].character = '#';
        }
    }

    private int findNearest(Room room, Room[] rooms, int minDistance, boolean isAllRoomsConnected) {
        int x1              = room.x + room.width / 2;
        int y1              = room.y + room.height / 2;
        int nearestIndex    = -1;
        int nearestDistance = 1000;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null && !rooms[i].equals(room)) {
                int x2 = rooms[i].x + rooms[i].width / 2;
                int y2 = rooms[i].y + rooms[i].height / 2;

                int distance = (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                if (distance < nearestDistance && distance > minDistance) {
                    nearestDistance = distance;
                    nearestIndex = i;
                }
            }
        }
        return nearestIndex;
    }

    private boolean checkRoomsIsConnected(Room[] rooms) {
        boolean isAllConnected = true;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null && !rooms[i].isConnected) {
                isAllConnected = false;
                break;
            }
        }
        return isAllConnected;
    }

    static class Room {

        int                x;
        int                y;
        int                width;
        int                height;
        boolean            isConnected;
        ArrayList<Integer> connected;

        Room() {
            connected = new ArrayList<>();
        }

        boolean intersects(Room other) {
            if (x + width < other.x) {
                return false;
            }
            if (y + height < other.y) {
                return false;
            }
            if (x > other.x + other.width) {
                return false;
            }
            if (y > other.y + other.height) {
                return false;
            }
            return true;
        }

        int distanceTo(Room other) {
            int x1 = x + width / 2;
            int y1 = y + height / 2;
            int x2 = other.x + other.width / 2;
            int y2 = other.y + other.height / 2;

            return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        }
    }

    private static class ConnectionException extends Exception {

    }
}
