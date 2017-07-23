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
package com.gmail.socraticphoenix.ill.information;

import com.gmail.socraticphoenix.ill.Cell;
import com.gmail.socraticphoenix.ill.Information;
import com.gmail.socraticphoenix.ill.Light;
import com.gmail.socraticphoenix.ill.ProgramMatrix;

public class CharGatherInformation implements Information {
    private int content;
    private int turnCount;

    public CharGatherInformation(int content) {
        this.content = content;
        this.turnCount = 1;
    }

    public int getContent() {
        return this.content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public boolean isExpired(Light light, Cell cell, ProgramMatrix matrix) {
        return this.turnCount <= 0;
    }

    @Override
    public void onExpiration(Light light, Cell cell, ProgramMatrix matrix) {
        apply(light);
    }

    @Override
    public void tick(Light light, Cell cell, ProgramMatrix matrix) {
        this.content = cell.getContent();
        light.setIntensity(15);
        this.turnCount--;
    }

    @Override
    public void postTick(Light light, Cell cell, ProgramMatrix matrix) {
        
    }

    @Override
    public void onDim(Light light, Cell cell, ProgramMatrix matrix) {
        
    }

    private void apply(Light light) {
        light.setIntensity(this.content);
    }

    @Override
    public Information copy() {
        CharGatherInformation n = new CharGatherInformation(this.content);
        n.turnCount = this.turnCount;
        return n;
    }

}
