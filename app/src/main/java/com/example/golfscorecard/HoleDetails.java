package com.example.golfscorecard;

import java.util.Arrays;
import java.util.Date;

public class  HoleDetails {

    Date date;
    String hole;
    String par;
    String score;
    StringBuilder fairway;
    StringBuilder approach;
    String gir;
    StringBuilder puttLengths;
    String totalPutts;
    String tees;
    String distanceToHole;
    String approachDistance;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHole() {
        return hole;
    }

    public void setHole(String hole) {
        this.hole = hole;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public StringBuilder getFairway() {
        return fairway;
    }

    public void setFairway(StringBuilder fairway) {
        this.fairway = fairway;
    }

    public StringBuilder getApproach() {
        return approach;
    }

    public void setApproach(StringBuilder approach) {
        this.approach = approach;
    }

    public String getGir() {
        return gir;
    }

    public void setGir(String gir) {
        this.gir = gir;
    }

    public StringBuilder getPuttLengths() {
        return puttLengths;
    }

    public void setPuttLengths(StringBuilder puttLengths) {
        this.puttLengths = puttLengths;
    }

    public String getTotalPutts() {
        return totalPutts;
    }

    public void setTotalPutts(String totalPutts) {
        this.totalPutts = totalPutts;
    }

    public String getTees() {
        return tees;
    }

    public void setTees(String tees) {
        this.tees = tees;
    }

    public String getDistanceToHole() {
        return distanceToHole;
    }

    public void setDistanceToHole(String distanceToHole) {
        this.distanceToHole = distanceToHole;
    }

    public String getApproachDistance() {
        return approachDistance;
    }

    public void setApproachDistance(String approachDistance) {
        this.approachDistance = approachDistance;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(hole).append(",")
                .append(par).append(",")
                .append(score).append(",")
                .append(nullCheck(fairway)).append(",")
                .append(nullCheck(approach)).append(",")
                .append(gir).append(",")
                .append(nullCheck(puttLengths)).append(",")
                .append(totalPutts).append(",")
                .append(tees).append(",")
                .append(distanceToHole).append(",")
                .append(approachDistance);
        return sb.toString();
        //Arrays.stream(puttLengths).forEach(System.out::println)
    }

    private String nullCheck(StringBuilder sb) {
        return (sb == null)?"":sb.toString();
    }
}
