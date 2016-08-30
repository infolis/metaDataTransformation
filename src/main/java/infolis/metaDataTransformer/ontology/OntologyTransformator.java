package infolis.metaDataTransformer.ontology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 *
 * @author domi
 */
public class OntologyTransformator {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        File dirMeta = new File("C:\\Users\\domi\\InFoLis2\\dataFormats\\meta");
        Map<String, File> metaFiles = new HashMap<>();
        for (File f : dirMeta.listFiles()) {
            String name = f.getName().replace(".json", "");
            metaFiles.put(name, f);
        }
        File inGS = new File("C:\\Users\\domi\\InFoLis2\\dataFormats\\ontology3.tsv");

        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(inGS), "UTF8"));
        String line = read.readLine();

        Gson gson = new GsonBuilder().create();
        Record r = new Record();
        Map<String, Dataset> mapEntity = new HashMap<>();
        Map<String, EntityLink> mapLinks = new HashMap<>();
        r.setEntity(mapEntity);
        r.setEntityLink(mapLinks);

//        Entity e = new Entity();
//        r.setEntity(e);
//        e.setDataset(new HashMap<String, Dataset>());
        boolean firstDataSet = false;
        Map<String, JsonObject> doiToRecord = new HashMap<>();
        int i = 0;
        while (line != null) {

            if (!line.contains("\t")) {
                line = read.readLine();
                firstDataSet = true;
                if (metaFiles.containsKey(line.split("\t")[0])) {
                    BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(metaFiles.get(line.split("\t")[0])), "UTF8"));
                    JsonReader reader = Json.createReader(is);
                    JsonObject obj = reader.readObject();
                    JsonObject response = obj.getJsonObject("response");
                    JsonArray resultDocs = response.getJsonArray("docs");

                    for (JsonObject result : resultDocs.getValuesAs(JsonObject.class)) {
                        if (result.getJsonArray("doi") != null) {
                            String doi = result.getJsonArray("doi").getString(0);
                            doiToRecord.put(doi, result);
                        }
                    }
                }
                else if(line.split("\t")[0].contains("SOEP")) {
                    BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(metaFiles.get("Sozio-ökonomisches Panel (SOEP)")), "UTF8"));
                    JsonReader reader = Json.createReader(is);
                    JsonObject obj = reader.readObject();
                    JsonObject response = obj.getJsonObject("response");
                    JsonArray resultDocs = response.getJsonArray("docs");

                    for (JsonObject result : resultDocs.getValuesAs(JsonObject.class)) {
                        if (result.getJsonArray("doi") != null) {
                            String doi = result.getJsonArray("doi").getString(0);
                            doiToRecord.put(doi, result);
                        }
                    }
                }
                else if(line.split("\t")[0].contains("SHARE")) {
                    BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(metaFiles.get("Survey of Health, Ageing and Retirement in Europe (SHARE)")), "UTF8"));
                    JsonReader reader = Json.createReader(is);
                    JsonObject obj = reader.readObject();
                    JsonObject response = obj.getJsonObject("response");
                    JsonArray resultDocs = response.getJsonArray("docs");

                    for (JsonObject result : resultDocs.getValuesAs(JsonObject.class)) {
                        if (result.getJsonArray("doi") != null) {
                            String doi = result.getJsonArray("doi").getString(0);
                            doiToRecord.put(doi, result);
                        }
                    }
                }
                continue;
            }

            if (firstDataSet) {
                String[] entries = line.split("\t");
                String series = entries[0];

                Dataset d = new Dataset();
                d.setName(series);
                d.setReliability(1.0);
                Set<String> tags = new HashSet<>();
                tags.add("infolis-ontology");
                d.setTags(tags);

                String name = "dataset_" + series.replaceAll("\\s", "").replaceAll("/", "").replaceAll("\\.", "").toLowerCase().replaceAll("\\(.*\\)", "").replaceAll("-", "").replaceAll(",", "").trim();
                mapEntity.put(name, d);
            }

            String[] entries = line.split("\t");
            String series = entries[0];
            String study = entries[1];
            String relation = entries[2];
            String identifier ="";
            if(entries.length>3) {
                identifier = entries[4];
            }

            Dataset d = new Dataset();
            d.setName(study);
            d.setIdentifier(identifier);
            d.setReliability(1.0);
            Set<String> tags = new HashSet<>();
            tags.add("infolis-ontology");
            d.setTags(tags);
            String name ="";
            if(identifier.length()<2) {
                name = "dataset_" + study.replaceAll("/", "").replaceAll("\\.", "").toLowerCase().replaceAll("\\(.*\\)", "").replaceAll("-", "").replaceAll(",", "").trim();
            }
            else {
                name = "dataset_" + identifier.substring(2).replaceAll("/", "").replaceAll("\\.", "").toLowerCase().replaceAll("\\(.*\\)", "").replaceAll("-", "").replaceAll(",", "").trim();
            }
            if (doiToRecord.containsKey(identifier)) {
                if (doiToRecord.get(identifier).getJsonArray("geographicCoverage") != null) {
                    JsonArray geos = doiToRecord.get(identifier).getJsonArray("geographicCoverage");
                    for (JsonValue v : geos) {
                        String stringValue = v.toString();
                        stringValue = stringValue.replaceAll("\"", "");
                        if (!stringValue.isEmpty() && !stringValue.equals("\"\"")) {
                            if (d.getSpatial() != null) {
                                d.getSpatial().add(stringValue);
                            } else {
                                Set<String> values = new HashSet<>();
                                values.add(stringValue);
                                d.setSpatial(values);
                            }
                        }
                    }
                }
                if (doiToRecord.get(identifier).getJsonArray("temporalCoverage") != null) {
                    JsonArray geos = doiToRecord.get(identifier).getJsonArray("temporalCoverage");
                    for (JsonValue v : geos) {
                        String stringValue = v.toString();
                        stringValue = stringValue.replaceAll("\"", "");
                        if (!stringValue.isEmpty() && !stringValue.equals("\"\"")) {
                            if (d.getNumericInfo() != null) {
                                d.getNumericInfo().add(stringValue);
                            } else {
                                Set<String> values = new HashSet<>();
                                values.add(stringValue);
                                d.setNumericInfo(values);
                            }
                        }
                    }
                }
            }

            mapEntity.put(name, d);

            if (mapLinks.containsKey("entityLink_" + name)) {
                mapLinks.get("entityLink_" + name).getEntityRelations().add(relation);
            } else {
                EntityLink e = new EntityLink();
                e.setFromEntity(name);
                e.setToEntity("dataset_" + series.replaceAll("\\s", "").replaceAll("/", "").replaceAll("\\.", "").toLowerCase().replaceAll("\\(.*\\)", "").replaceAll("-", "").replaceAll(",", "").trim());
                e.setConfidence(1.0);
                e.setLinkReason("infolis-ontology");
                List<String> rels = new ArrayList<>();
                rels.add(relation);
                e.setEntityRelations(rels);
                mapLinks.put("entityLink_" + name, e);
            }

            firstDataSet = false;

            line = read.readLine();
        }

        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("ontov5.json"))));        
        write.write(gson.toJson(r));
        write.flush();

//	JSONArray list = new JSONArray();
//	list.add("msg 1");
//	list.add("msg 2");
//	list.add("msg 3");
//
//	obj.put("messages", list);
//	try {
//
//		FileWriter file = new FileWriter("c:\\test.json");
//		file.write(obj.toJSONString());
//		file.flush();
//		file.close();
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
    }

}
