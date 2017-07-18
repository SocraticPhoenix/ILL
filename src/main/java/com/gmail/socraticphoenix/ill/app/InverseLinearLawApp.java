/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 socraticphoenix@gmail.com
 * Copyright (c) 2017 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.ill.app;

import com.gmail.socraticphoenix.ill.CharMatrix;
import com.gmail.socraticphoenix.ill.ProgramMatrix;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InverseLinearLawApp {

    public static void main(String[] in) throws IOException {
        List<String> args = new ArrayList<>();
        Map<String, String> flags = new LinkedHashMap<>();
        for(String arg : in) {
            if(arg.startsWith("-")) {
                String[] pieces = arg.replaceFirst("-", "").split("=", 2);
                flags.put(pieces[0], pieces.length > 1 ? pieces[1] : "");
            } else {
                args.add(arg);
            }
        }

        if(flags.containsKey("h")) {
            System.out.println("arg1, <file>: the program to load");
            System.out.println("-h: display this message (cancels all other flags)");
            System.out.println("-b: display byte count");
            System.out.println("-r: run the program (on by default if no other flags are specified)");
            return;
        }

        if(args.size() != 1) {
            System.out.println("Expected 1 argument: <file>");
        }

        String content = new String(Files.readAllBytes(Paths.get(args.get(0))), StandardCharsets.UTF_8);

        if(flags.containsKey("b")) {
            System.out.println("Bytes: " + content.getBytes(StandardCharsets.UTF_8).length);
        }

        if(flags.containsKey("s")) {
            Files.write(Paths.get(flags.get("s")), content.getBytes(StandardCharsets.UTF_8));
        }

        if(flags.containsKey("r") || flags.isEmpty()) {
            ProgramMatrix matrix = new ProgramMatrix(new CharMatrix(content));
            matrix.runToCompletion();
        }

    }

}
