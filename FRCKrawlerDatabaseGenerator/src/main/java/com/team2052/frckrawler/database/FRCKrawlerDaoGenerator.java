package com.team2052.frckrawler.database;

import java.util.Map;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class FRCKrawlerDaoGenerator
{

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(4, "frckrawler");
        //Games
        Entity game = schema.addEntity("Game");
        game.implementsSerializable();
        game.addIdProperty().autoincrement().unique();
        game.addStringProperty("name");

        //Events
        Entity event = schema.addEntity("Event");
        event.implementsSerializable();
        event.addIdProperty().autoincrement().unique();
        event.addStringProperty("name");
        event.addToOne(game, event.addLongProperty("gameId").getProperty());
        event.addStringProperty("location");
        event.addDateProperty("date");
        event.addStringProperty("fmsid").unique();

        Entity team = schema.addEntity("Team");
        team.implementsSerializable();
        team.addLongProperty("number").unique().primaryKey();
        team.addStringProperty("teamkey").unique();
        team.addStringProperty("name");
        team.addStringProperty("location");
        team.addIntProperty("rookieYear");
        team.addStringProperty("website");

        Entity user = schema.addEntity("User");
        user.implementsSerializable();
        user.addIdProperty().autoincrement().unique();
        user.addStringProperty("name");

        Entity contact = schema.addEntity("Contact");
        contact.implementsSerializable();
        contact.addIdProperty().autoincrement().unique();
        contact.addToOne(team, contact.addLongProperty("teamId").getProperty());
        contact.addStringProperty("name");
        contact.addStringProperty("email");
        contact.addStringProperty("address");
        contact.addStringProperty("phonenumber");
        contact.addStringProperty("teamrole");

        Entity match = schema.addEntity("Match");
        match.implementsSerializable();
        match.addIdProperty().autoincrement().unique();
        match.addToOne(event, match.addLongProperty("eventId").getProperty());
        match.addStringProperty("key").unique();
        match.addIntProperty("number");
        match.addStringProperty("type");
        match.addToOne(team, match.addLongProperty("blue1Id").getProperty(), "blue1");
        match.addToOne(team, match.addLongProperty("blue2Id").getProperty(), "blue2");
        match.addToOne(team, match.addLongProperty("blue3Id").getProperty(), "blue3");
        match.addToOne(team, match.addLongProperty("red1Id").getProperty(), "red1");
        match.addToOne(team, match.addLongProperty("red2Id").getProperty(), "red2");
        match.addToOne(team, match.addLongProperty("red3Id").getProperty(), "red3");

        match.addIntProperty("redscore");
        match.addIntProperty("bluescore");

        Entity robot = schema.addEntity("Robot");
        robot.implementsSerializable();
        robot.addIdProperty().unique().autoincrement();
        robot.addToOne(team, robot.addLongProperty("teamId").getProperty());
        robot.addToOne(game, robot.addLongProperty("gameId").getProperty());
        robot.addStringProperty("comments");
        robot.addDoubleProperty("opr");

        Entity robotEvent = schema.addEntity("RobotEvent");
        robotEvent.implementsSerializable();
        robotEvent.addIdProperty().unique().autoincrement();
        robotEvent.addToOne(robot, robotEvent.addLongProperty("robotId").getProperty());
        robotEvent.addToOne(event, robotEvent.addLongProperty("eventId").getProperty());

        Entity metric = schema.addEntity("Metric");
        metric.implementsSerializable();
        metric.addIdProperty().unique().autoincrement();
        metric.addStringProperty("name");
        metric.addIntProperty("category");
        metric.addStringProperty("description");
        metric.addIntProperty("type");
        metric.addStringProperty("range");
        metric.addToOne(game, metric.addLongProperty("gameId").getProperty());

        Entity robotPhoto = schema.addEntity("RobotPhoto");
        robotPhoto.implementsSerializable();
        robotPhoto.addIdProperty().autoincrement().unique();
        robotPhoto.addStringProperty("location");
        robotPhoto.addToOne(robot, robotPhoto.addLongProperty("robotId").getProperty());

        Entity matchData = schema.addEntity("MatchData");
        matchData.implementsSerializable();
        matchData.addStringProperty("data");
        matchData.addToOne(robot, matchData.addLongProperty("robotId").getProperty());
        matchData.addToOne(metric, matchData.addLongProperty("metricId").getProperty());
        matchData.addToOne(match, matchData.addLongProperty("matchId").getProperty());


        Entity pitData = schema.addEntity("PitData");
        pitData.implementsSerializable();
        pitData.addStringProperty("data");
        pitData.addToOne(robot, pitData.addLongProperty("robotId").getProperty());
        pitData.addToOne(metric, pitData.addLongProperty("metricId").getProperty());
        pitData.addToOne(event, pitData.addLongProperty("eventId").getProperty());

        Entity matchComment = schema.addEntity("MatchComment");
        matchComment.implementsSerializable();
        matchComment.addToOne(robot, matchComment.addLongProperty("robotId").getProperty());
        matchComment.addToOne(match, matchComment.addLongProperty("matchId").getProperty());
        matchComment.addStringProperty("comment");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
