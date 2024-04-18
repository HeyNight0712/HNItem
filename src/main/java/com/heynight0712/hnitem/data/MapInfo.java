package com.heynight0712.hnitem.data;

import java.time.LocalDate;

public class MapInfo {
    private final int mapID;
    private final String uuid;
    private final String name;
    private final boolean locked;
    private final LocalDate date;

    public MapInfo(int mapID, String uuid, String name, boolean locked, LocalDate date) {
        this.mapID = mapID;
        this.uuid = uuid;
        this.name = name;
        this.locked = locked;
        this.date = date;
    }

    public int getMapID() {return mapID;}
    public String getUuid() {return uuid;}
    public String getName() {return name;}

    public boolean getLocked() {return locked;}
    public LocalDate getDate() {return date;}

    @Override
    public String toString() {
        return "MapInfo{" +
                "mapID=" + mapID +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", locked=" + locked + '\'' +
                ", date=" + date +
                '}';
    }
}
