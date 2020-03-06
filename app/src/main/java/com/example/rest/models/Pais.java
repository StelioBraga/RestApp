package com.example.rest.models;

import org.json.JSONArray;

public class Pais {
       String name;
       String capital;
       String region;
       String sub_region;
       int population;
       double area;
       JSONArray timezones; // array of strings
       String nativeName;
       String flag;

       public Pais(String name, String capital, String region, int population, double area, JSONArray timezones, String nativeName, String flag, String sub_region) {
              this.name = name;
              this.capital = capital;
              this.region = region;
              this.population = population;
              this.area = area;
              this.timezones = timezones;
              this.nativeName = nativeName;
              this.flag = flag;
              this.sub_region = sub_region;
       }



       // methods
       public static String[] getAllKeysInPT() {
              return new String[] {
                      "Nome",
                      "Capital",
                      "Regiao",
                      "Populacao",
                      "Area",
                      "Fusos-Horarios",
                      "Nome Nativo",
                      "Link da Bandeira",
                      "Sub-Regiao"
              };
       }



       public String getName() {
              return name;
       }

       public void setName(String name) {
              this.name = name;
       }

       public String getCapital() {
              return capital;
       }

       public void setCapital(String capital) {
              this.capital = capital;
       }

       public String getRegion() {
              return region;
       }

       public void setRegion(String region) {
              this.region = region;
       }

       public int getPopulation() {
              return population;
       }

       public void setPopulation(int population) {
              this.population = population;
       }

       public double getArea() {
              return area;
       }

       public void setArea(double area) {
              this.area = area;
       }

       public JSONArray getTimezones() {
              return timezones;
       }

       public void setTimezones(JSONArray timezones) {
              this.timezones = timezones;
       }

       public String getNativeName() {
              return nativeName;
       }

       public void setNativeName(String nativeName) {
              this.nativeName = nativeName;
       }

       public String getFlag() {
              return flag;
       }

       public void setFlag(String flag) {
              this.flag = flag;
       }

       public String getSub_region() {
              return sub_region;
       }

       public void setSub_region(String sub_region) {
              this.sub_region = sub_region;
       }
}
