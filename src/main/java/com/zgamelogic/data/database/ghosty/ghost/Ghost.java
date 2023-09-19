package com.zgamelogic.data.database.ghosty.ghost;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name = "Ghosts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ghost {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "description", length = 5000)
    private String description;
    private String evidence;

    public Ghost(JSONObject body) {
        try {
            id = body.getInt("id");
        }catch(JSONException e) {

        }

        try {
            name = body.getString("name");
            description = body.getString("description");
            evidence = body.getString("evidence");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGhost(Ghost newGhost) {
        this.name = newGhost.getName();
        this.description = newGhost.getDescription();
        this.evidence = newGhost.getEvidence();
    }
}