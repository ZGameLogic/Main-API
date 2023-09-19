package com.zgamelogic.data.database.ghosty.aspect;

import jakarta.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Aspects")
@ToString
@Getter
public class Aspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String content;
    private String type;

    public Aspect(JSONObject body) {
        try {
            id = body.getInt("id");
        }catch(JSONException e) {

        }

        try {
            name = body.getString("name");
            content = body.getString("content");
            type = body.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAspect(Aspect aspect) {
        this.name = aspect.name;
        this.content = aspect.content;
        this.type = aspect.type;
    }
}