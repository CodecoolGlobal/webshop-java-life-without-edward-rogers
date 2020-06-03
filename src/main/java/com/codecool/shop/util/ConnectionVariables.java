package com.codecool.shop.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {

        private String filePath;
        private Integer fromLine;
        private Integer toLine;

        public void setup(String filePath, Integer fromLine, Integer toLine) throws IllegalArgumentException{
            if (toLine < fromLine || fromLine < 1){
                throw new IllegalArgumentException();
            }
            this.filePath = filePath;
            this.fromLine = fromLine;
            this.toLine = toLine;
        }

        public String read() throws IOException {
            StringBuilder content = new StringBuilder();
            if (!new FileReader(filePath).ready()){
                throw new IOException();
            }
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            content.deleteCharAt(content.lastIndexOf("\n"));
            return content.toString();
        }

        public String readLines () throws IOException {
            String content = read();
            StringBuilder contentLines = new StringBuilder();
            ArrayList<String> linesOfContent = new ArrayList<>(Arrays.asList(content.split("\n")));
            int fromLineIndex = fromLine-1;
            int toLineIndex = toLine-1;
            for (int i = fromLineIndex; i <= toLineIndex; i++) {
                if (linesOfContent.size() > i){
                    contentLines.append(linesOfContent.get(i)).append("\n");
                } else {
                    break;
                }
            }
            contentLines.deleteCharAt(contentLines.lastIndexOf("\n"));
            return contentLines.toString();
        }


    }
}
