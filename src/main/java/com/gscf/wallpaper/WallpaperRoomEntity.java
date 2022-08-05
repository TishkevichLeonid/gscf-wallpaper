package com.gscf.wallpaper;

import java.util.Objects;

public class WallpaperRoomEntity implements Comparable<WallpaperRoomEntity> {

    private String roomId;

    private int square;

    public WallpaperRoomEntity(String roomId, int square) {
        this.roomId = roomId;
        this.square = square;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    @Override
    public int compareTo(WallpaperRoomEntity o) {
        return Integer.compare(o.square, this.square);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallpaperRoomEntity that = (WallpaperRoomEntity) o;

        if (square != that.square) return false;
        return Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + square;
        return result;
    }

    @Override
    public String toString() {
        return roomId;
    }
}
