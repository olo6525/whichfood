package com.coin.whichfood;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApiModel {

    private String dt;

    private POJO_Coord coord;

    private POJO_Weather[] weather;

    private String name;

    private String cod;

    private POJO_Main main;

    private POJO_Clouds clouds;

    private String id;

    private POJO_Sys sys;

    private String base;

    private POJO_Wind wind;

    public String getDt ()
    {
        return dt;
    }

    public void setDt (String dt)
    {
        this.dt = dt;
    }

    public POJO_Coord getCoord ()
    {
        return coord;
    }

    public void setCoord (POJO_Coord coord)
    {
        this.coord = coord;
    }

    public POJO_Weather[] getWeather ()
    {
        return weather;
    }

    public void setWeather (POJO_Weather[] weather)
    {
        this.weather = weather;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCod ()
    {
        return cod;
    }

    public void setCod (String cod)
    {
        this.cod = cod;
    }

    public POJO_Main getMain ()
    {
        return main;
    }

    public void setMain (POJO_Main main)
    {
        this.main = main;
    }

    public POJO_Clouds getClouds ()
    {
        return clouds;
    }

    public void setClouds (POJO_Clouds clouds)
    {
        this.clouds = clouds;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public POJO_Sys getSys ()
    {
        return sys;
    }

    public void setSys (POJO_Sys sys)
    {
        this.sys = sys;
    }

    public String getBase ()
    {
        return base;
    }

    public void setBase (String base)
    {
        this.base = base;
    }

    public POJO_Wind getWind ()
    {
        return wind;
    }

    public void setWind (POJO_Wind wind)
    {
        this.wind = wind;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dt = "+dt+", coord = "+coord+", weather = "+weather+", name = "+name+", cod = "+cod+", main = "+main+", clouds = "+clouds+", id = "+id+", sys = "+sys+", base = "+base+", wind = "+wind+"]";
    }
}
