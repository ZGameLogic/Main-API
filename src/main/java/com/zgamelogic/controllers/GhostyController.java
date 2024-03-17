package com.zgamelogic.controllers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgamelogic.data.database.ghosty.aspect.Aspect;
import com.zgamelogic.data.database.ghosty.aspect.AspectRepository;
import com.zgamelogic.data.database.ghosty.ghost.Ghost;
import com.zgamelogic.data.database.ghosty.ghost.GhostRepository;
import com.zgamelogic.data.generic.Aspects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ghosty")
@PropertySource("file:application.properties")
public class GhostyController {

    private final GhostRepository ghosts;
    private final AspectRepository aspects;

    @Value("${ghosty.api.key}")
    private String ghostyApiKey;

    @Autowired
    public GhostyController(GhostRepository ghosts, AspectRepository aspects) {
        this.ghosts = ghosts;
        this.aspects = aspects;
    }

    /**
     * @return JSONArray of ghosts JSONObjects
     */
    @GetMapping("Ghosts")
    public ResponseEntity<String> getAllGhosts() {
        try {
            JSONObject returnBody = new JSONObject();
            JSONArray ghostsArray = new JSONArray();
            ObjectMapper om = new ObjectMapper();
            for(Ghost current : ghosts.findAll()) {
                ghostsArray.put(om.writeValueAsString(current));
            }
            returnBody.put("ghosts", ghostsArray);
            return ResponseEntity.ok(returnBody.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return JSONArray of ghosts JSONObjects
     */
    @GetMapping("Ghosts2")
    public ResponseEntity<String> getAllGhostsV2() {
        try {
            JSONObject returnBody = new JSONObject();
            JSONArray ghostsArray = new JSONArray();
            for(Ghost current : ghosts.findAll()) {
                JSONObject ghost = new JSONObject();
                JSONArray evidenceArray = new JSONArray();
                for(String e : current.getEvidence().split(",")) {
                    evidenceArray.put(e);
                }
                ghost.put("evidence", evidenceArray);
                ghost.put("name", current.getName());
                ghost.put("description", current.getDescription());
                ghost.put("id", current.getId());
                ghostsArray.put(ghost);
            }
            returnBody.put("ghosts", ghostsArray);
            return ResponseEntity.ok(returnBody.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    /**
     * @return JSONArray of ghosts JSONObjects
     */
    @GetMapping("Ghosts3")
    public ResponseEntity<String> getAllGhostsV3() {
        try {
            JSONArray ghostsArray = new JSONArray();
            for(Ghost current : ghosts.findAll()) {
                JSONObject ghost = new JSONObject();
                JSONArray evidenceArray = new JSONArray();
                for(String e : current.getEvidence().split(",")) {
                    evidenceArray.put(e);
                }
                ghost.put("evidence", evidenceArray);
                ghost.put("name", current.getName());
                ghost.put("description", current.getDescription());
                ghost.put("id", current.getId());
                ghostsArray.put(ghost);
            }
            return ResponseEntity.ok(ghostsArray.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    /**
     * @return JSONArray of Aspect JSONObjects
     */
    @GetMapping("Aspects")
    public ResponseEntity<Aspects> getAllAspects() {
        Aspects body = new Aspects(aspects.findAll());
        return ResponseEntity.ok(body);
    }

    /**
     * @return JSONArray of Aspect JSONObjects
     */
    @GetMapping("Aspects3")
    public ResponseEntity<String> getAllAspectsV2() {
        try {
            JSONArray aspectsArray = new JSONArray();
            ObjectMapper om = new ObjectMapper();
            for(Aspect current : aspects.findAll()) {
                aspectsArray.put(om.writeValueAsString(current));
            }
            return ResponseEntity.ok(aspectsArray.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Use this request to get a list of aspect types
     * @return list of aspect types
     */
    @GetMapping("AspectTypes")
    public ResponseEntity<String> getAllAspectTypes() {
        try {
            JSONObject returnBody = new JSONObject();
            Set<String> types = new HashSet<String>();

            for(Aspect current : aspects.findAll()) {
                types.add(current.getType());
            }

            JSONArray aspectTypesArray = new JSONArray(types);

            returnBody.put("aspectTypes", aspectTypesArray);
            return ResponseEntity.ok(returnBody.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    /**
     * @return A list of evidence
     */
    @GetMapping("Evidence")
    public ResponseEntity<String> getAllEvidence() {
        try {
            JSONObject returnBody = new JSONObject();
            Set<String> types = new HashSet<String>();

            for(Ghost current : ghosts.findAll()) {
                for(String x : current.getEvidence().split(",")) {
                    if(!x.equals("")) {
                        types.add(x);
                    }
                }
            }

            JSONArray ghostsArray = new JSONArray(types);

            returnBody.put("evidence", ghostsArray);
            return ResponseEntity.ok(returnBody.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    /**
     * @return A list of evidence
     */
    @GetMapping("Evidence2")
    public ResponseEntity<String> getAllEvidenceV3() {
        try {
            Set<String> types = new HashSet<String>();

            for(Ghost current : ghosts.findAll()) {
                for(String x : current.getEvidence().split(",")) {
                    if(!x.equals("")) {
                        types.add(x);
                    }
                }
            }

            JSONArray ghostsArray = new JSONArray(types);
            return ResponseEntity.ok(ghostsArray.toString());
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    /**
     * @param key apiKey for the program
     * @param ghostID ID of record to be deleted
     * @return Response to delete request
     */
    @DeleteMapping("Ghosts/{ghostID:.+}")
    public ResponseEntity<String> deleteGhost(@RequestHeader(value="apiKey") String key, @PathVariable("ghostID") int ghostID) {
        try {
            if(validateKey(key)) {
                if(ghosts.existsById(ghostID)){
                    ghosts.deleteById(ghostID);
                    return ResponseEntity.ok("Ghost deleted");
                }

            }
            return ResponseEntity.ok("Invalid apiKey");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("Invalid project id");
        }
    }

    /**
     * @param key apiKey for the program
     * @param aspectID ID of record to be deleted
     * @return Response to delete request
     */
    @DeleteMapping("Aspects/{aspectID:.+}")
    public ResponseEntity<String> deleteAspect(@RequestHeader(value="apiKey") String key, @PathVariable("aspectID") int aspectID) {
        try {
            if(validateKey(key)) {
                if(aspects.existsById(aspectID)) {
                    aspects.deleteById(aspectID);
                    return ResponseEntity.ok("Aspect deleted");
                }
                return ResponseEntity.ok("Invalid id");
            }
            return ResponseEntity.ok("Invalid apiKey");
        } catch (IllegalArgumentException e1) {
            return ResponseEntity.ok("Invalid project id");
        }
    }

    @PostMapping("Ghosts")
    public ResponseEntity<String> saveGhost(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
        try {
            JSONObject body = new JSONObject(bodyString);
            if(validateKey(key)) {
                if(body.has("evidence") && body.has("name") && body.has("description")) {
                    Ghost newGhost = new Ghost(body);
                    ghosts.save(newGhost);
                    return ResponseEntity.ok("Ghost saved");
                }
                return ResponseEntity.ok("Ghost not saved! We need evidence, name, and decription");
            }
            return ResponseEntity.ok("Invalid apiKey");
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }

    @PostMapping("Aspects")
    public ResponseEntity<String> saveAspect(@RequestHeader(value="apiKey") String key, @RequestBody String bodyString) {
        try {
            JSONObject body = new JSONObject(bodyString);
            if(validateKey(key)) {
                if(body.has("name") && body.has("content") && body.has("type")) {
                    Aspect newAspect = new Aspect(body);
                    aspects.save(newAspect);
                    return ResponseEntity.ok("Content saved");
                }
                return ResponseEntity.ok("Content not saved! We need content, type and name");
            }
            return ResponseEntity.ok("Invalid apiKey");
        } catch (JSONException e) {
            return ResponseEntity.ok("Invalid JSON format");
        }
    }


    private boolean validateKey(String key) {
        return key.equals(ghostyApiKey);
    }
}