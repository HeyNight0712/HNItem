package com.heynight0712.hnitem.data;

import java.time.LocalDate;

public record MapInfo(int mapID, String uuid, String name, boolean locked, LocalDate date) {
    /**
     * 使用 MapDatabase.getMap 返回的
     *
     * @param mapID  地圖 ID
     * @param uuid   擁有者 UUID
     * @param name   地圖名稱
     * @param locked 是否鎖定 (開放複製)
     * @param date   製作日期
     */
    public MapInfo {
    }

    /**
     * 獲取地圖 ID
     *
     * @return 返回 map id
     */
    @Override
    public int mapID() {
        return mapID;
    }

    /**
     * 獲取 擁有者 UUID
     *
     * @return 返回 擁有者 UUID
     */
    @Override
    public String uuid() {
        return uuid;
    }

    /**
     * 獲取 地圖名稱
     *
     * @return 返回 地圖名稱
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * 獲取 是否鎖定
     *
     * @return 如果鎖定 則返回 True
     */
    @Override
    public boolean locked() {
        return locked;
    }

    /**
     * 獲取 製造日期
     *
     * @return 返回 製造日期
     */
    @Override
    public LocalDate date() {
        return date;
    }

    /**
     * 獲取全部資訊
     *
     * @return 返回 全部資訊
     */
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
