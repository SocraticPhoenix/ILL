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

import com.gmail.socraticphoenix.collect.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cell {
    private List<Light> light;
    private Instruction instruction;
    private int content;

    public Cell(List<Light> light, Instruction instruction, int content) {
        this.light = light;
        this.instruction = instruction;
        this.content = content;
    }

    public Cell(Instruction instruction, int content) {
        this.instruction = instruction;
        this.content = content;
        this.light = new ArrayList<>();
    }

    public Cell darkCopy() {
        return new Cell(this.instruction, this.content);
    }

    public List<Light> getLight() {
        return this.light;
    }

    public Instruction getInstruction() {
        return this.instruction;
    }

    public int getContent() {
        return this.content;
    }

    public void onTick(ProgramMatrix matrix) {
        for(Light l : this.light) {
            Iterator<Information> iter = l.getInformation().iterator();
            while (iter.hasNext()) {
                Information i = iter.next();
                if(i.isExpired(l, this, matrix)) {
                    i.onExpiration(l, this, matrix);
                    iter.remove();
                } else {
                    i.tick(l, this, matrix);
                }
            }
        }
        if(this.instruction != null) {
            Items.looseClone(this.light).forEach(l -> instruction.onTick(l, this, matrix));
        }
    }

    public void removeDimmed(ProgramMatrix matrix) {
        Iterator<Light> iter = this.light.iterator();
        while (iter.hasNext()) {
            Light l = iter.next();
            if(l.hasDimmed()) {
                iter.remove();
                if(this.instruction != null) {
                    this.instruction.onDim(l, matrix);
                }
                Items.looseClone(l.getInformation(), ArrayList::new).forEach(i -> i.onDim(l, this, matrix));
            }
        }
    }

    public void onDim(ProgramMatrix matrix, Light light) {
        if(this.instruction != null) {
            instruction.onDim(light, matrix);
        }
    }

    public void postTick(ProgramMatrix matrix, Cell newCell, Light light) {
        if(this.instruction != null) {
            Items.looseClone(this.light).forEach(l -> Items.looseClone(l.getInformation(), ArrayList::new).forEach(i -> i.postTick(l, newCell, matrix)));
            instruction.postTick(light, newCell, matrix);
        }
    }

}
