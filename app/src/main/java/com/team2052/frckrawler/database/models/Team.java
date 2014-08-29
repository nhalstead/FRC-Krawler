package com.team2052.frckrawler.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Adam
 */
@Table(name = "teams")
public class Team extends Model {
    @Column(name = "TeamKey", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    public String teamKey;

    @Column(name = "Number", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    public int number;

    @Column(name = "Name")
    public String name;

    @Column(name = "Location")
    public String location;

    @Column(name = "RookieYear")
    public int rookieYear;

    @Column(name = "Website")
    public String website;

    public Team(String teamKey, int number, String name, String location, int rookieYear, String website) {
        this.teamKey = teamKey;
        this.number = number;
        this.name = name;
        this.location = location;
        this.rookieYear = rookieYear;
        this.website = website;
    }

    public Team() {
    }
}
