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
package com.gmail.socraticphoenix.ill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CharMatrix {
    private int[][] matrix; //row by column (y by x)
    private int width;
    private int height;

    public CharMatrix(String content) {
        BufferedReader reader = new BufferedReader(new StringReader(content));
        List<String> strs = new ArrayList<>();
        int maxlen = 0;
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if(line.equals("#END OF FILE#")) {
                    break;
                }
                strs.add(line);
                int ln = (int) line.codePoints().count();
                if(ln > maxlen) {
                    maxlen = ln;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("String reader threw IO exception", e);
        }

        this.matrix = new int[strs.size()][maxlen];
        for (int y = 0; y < strs.size(); y++) {
            int[] cdpts = strs.get(y).codePoints().toArray();
            int[] row = this.matrix[y];
            for (int x = 0; x < row.length; x++) {
                if(x < cdpts.length) {
                    row[x] = cdpts[x];
                } else {
                    row[x] = ' ';
                }
            }
        }

        this.width = maxlen;
        this.height = strs.size();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getChar(int x, int y) {
        return this.matrix[y][x];
    }

    public void iterate(BiConsumer<Loc, Integer> iter) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                iter.accept(new Loc(x, y), this.getChar(x, y));
            }
        }
    }

}
