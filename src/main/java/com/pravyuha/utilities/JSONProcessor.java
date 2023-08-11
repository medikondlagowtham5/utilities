package com.pravyuha.utilities;

        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.SerializationFeature;
        import com.fasterxml.jackson.databind.node.ArrayNode;
        import com.fasterxml.jackson.databind.node.ObjectNode;
        import java.io.File;

public class JSONProcessor {
    public static void generateJsonFile(String value) {
        File inputFile = new File("C:\\Users\\Gowtham.P\\Downloads\\FinalConfig_F1.cfg");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            //deserialize
            ObjectNode rootNode = (ObjectNode) objectMapper.readTree(inputFile);


            // modify
            ArrayNode advSetArray = (ArrayNode) rootNode.path("advSet");
            if (advSetArray.isArray()) {
                for (JsonNode advSetItem : advSetArray) {
                    ArrayNode payloadArray = (ArrayNode) advSetItem.path("payload");
                    for (JsonNode payloadItem : payloadArray) {
                        JsonNode dataNode = payloadItem.path("data");
                        if (isNumeric(dataNode.asText())) {
                            ((ObjectNode) payloadItem).put("data", value); // Update the data
                        }
                    }

                }

                // Serialize

                String filename = convertToASCII(value);
                String outputFileName = "D:\\Pv\\" + filename + ".json";
                File outputFile = new File(outputFileName);
                objectMapper.writeValue(outputFile, rootNode);

                System.out.println("JSON file generated for value " + ": " + outputFile.getAbsolutePath());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
    private static String convertToASCII(String hexValue) {
        // Conversion logic to ASCII
        StringBuilder asciiText = new StringBuilder();
        for (int i = 0; i < hexValue.length(); i += 2) {
            String hexPair = hexValue.substring(i, i + 2);
            int decimalValue = Integer.parseInt(hexPair, 16);
            asciiText.append((char) decimalValue);
        }
        return asciiText.toString();
    }
}
/*
 public static void generateJsonFile(String value) {
        File inputFile = new File("D:\\PV\\FinalConfig_F1.cfg");
        File outputFile = new File("D:\\PV\\serialized_member.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            //deserialize
            NBConfig nbConfig = objectMapper.readValue(inputFile, NBConfig.class);

//modify
            for (AdvSet advSet : nbConfig.getAdvSet()){
                for (Newpayload newpayload : advSet.getNewpayload()){
                    //newpayload.setData("25");
                    if (newpayload.getData().matches("\\d+")) {
                        newpayload.setData(value);
                    }
                }
            }
            // Serialize
            objectMapper.writeValue(outputFile, nbConfig);
            System.out.println(nbConfig.getAdvSet().get(0).getNewpayload().get(0).getLen());

        } catch (Exception e) {
            e.printStackTrace();
        }

 */