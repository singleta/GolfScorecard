package com.example.golfscorecard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class  HoleDetails {

    LocalDate date;
    int hole;
    int par;
    int score;
    String fairway;
    String approach;
    String gir;
    String puttLengths;
    int totalPutts;
    String tees;
    String distanceToHole;
    String approachDistance;

    public static int[] parsByHole = new int[]{4,4,4,4,3,5,4,5,3,5,3,4,4,4,5,4,3,4};

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHole() {
        return hole;
    }

    public void setHole(int hole) {
        this.hole = hole;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = Integer.parseInt(score);
    }

    public String getFairway() {
        return fairway;
    }

    public void setFairway(String fairway) {
//        String convertedValue = fairway.toString().replaceAll("F", "1");
        this.fairway = fairway;
    }

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public String getGir() {
        return gir;
    }

    public void setGir(String gir) {
        this.gir = gir;
    }

    public String getPuttLengths() {
        return puttLengths;
    }

    public void setPuttLengths(String puttLengths) {
        this.puttLengths = puttLengths;
    }

    public int getTotalPutts() {
        return totalPutts;
    }

    public void setTotalPutts(String totalPutts) {
        this.totalPutts = Integer.parseInt(totalPutts);
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
        this.distanceToHole = distanceToHole.substring(0,1);
    }

    public String getApproachDistance() {
        return approachDistance;
    }

    public void setApproachDistance(String approachDistance) {
        this.approachDistance = approachDistance;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        sb.append(date.format(formatter)).append(",")
                .append(hole).append(",")
                .append(par).append(",")
                .append(score).append(",")
                .append(nullCheck(fairway)).append(",")
                .append(nullCheck(approach)).append(",")
                .append(gir).append(",")
                .append(totalPutts).append(",")
                .append(tees).append(",")
//                .append(nullCheck(puttLengths)).append(",")
                .append(distanceToHole).append(",")
                .append(approachDistance);
        return sb.toString();
        //Arrays.stream(puttLengths).forEach(System.out::println)
    }

    private String nullCheck(String s) {
        return (s == null)?"":s;
    }
}
