package com.darkwinter.bookfilms;

import java.io.Serializable;

/**
 * Created by DarkWinter on 10/27/17.
 */

public class Films implements Serializable {
    private String id;
    private String Name;
    private String Image;
    private String Descrip;
    private String Duration;
    private String Producer;
    private String Rating;
    private String Trailer;

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public Films(String id, String name, String image, String descrip, String duration, String producer, String rating, String trailer) {
        this.id = id;
        Name = name;
        Image = image;
        Descrip = descrip;
        Duration = duration;
        Producer = producer;
        Rating = rating;
        Trailer = trailer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public Films() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String descrip) {
        Descrip = descrip;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }
}
