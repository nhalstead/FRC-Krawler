package com.team2052.frckrawler.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Adam
 */
@Table(name = "robots")
public class Robot extends Model{
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int remoteId;

    @Column(name = "Team", onDelete = Column.ForeignKeyAction.CASCADE)
    public Team team;

    @Column(name = "Comments")
    public String comments;

    @Column(name = "Opr")
    public double opr;

    @Column(name = "Game", onDelete = Column.ForeignKeyAction.CASCADE)
    public Game game;

    public Robot(Team team, String comments, double opr, Game game) {
        this.remoteId = (int)(Math.random() * 1000000);
        this.team = team;
        this.comments = comments;
        this.opr = opr;
        this.game = game;
    }

    public Robot() {
    }
}
