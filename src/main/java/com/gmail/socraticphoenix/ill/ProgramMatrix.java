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

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ProgramMatrix {
    public static final int DEFAULT_INTENSITY = 15;

    private Cell[][] cells;
    private int width;
    private int height;

    public ProgramMatrix(CharMatrix matrix) {
        this.cells = new Cell[matrix.getHeight()][matrix.getWidth()];
        this.width = matrix.getWidth();
        this.height = matrix.getHeight();
        matrix.iterate((loc, ch) -> {
            Cell cell = new Cell(InstructionRegistry.getInstruction(ch), ch);
            if (ch == '~') {
                cell.getLight().add(new Light(DEFAULT_INTENSITY, Direction.EAST));
                cell.getLight().add(new Light(DEFAULT_INTENSITY, Direction.WEST));
            }
            this.set(loc, cell);
        });
    }

    public void set(Loc loc, Cell cell) {
        this.cells[loc.y][loc.x] = cell;
    }

    public Cell get(Loc loc) {
        return this.cells[loc.y][loc.x];
    }

    public boolean contains(Loc loc) {
        return loc.x >= 0 && loc.y >= 0 && loc.x < this.width && loc.y < this.height;
    }

    public void iterate(BiConsumer<Loc, Cell> iter) {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Loc loc = new Loc(x, y);
                iter.accept(loc, this.get(loc));
            }
        }
    }

    public void tick() {
        this.iterate((loc, cell) -> cell.onTick(this));
    }

    public void stepBoard(Consumer<Loc> onLight) {
        Cell[][] newMatrix = new Cell[this.height][this.width];
        this.iterate((loc, cell) -> newMatrix[loc.y][loc.x] = cell.darkCopy());
        this.iterate((loc, cell) -> {
            cell.removeDimmed(this);
            for (Light l : cell.getLight()) {
                l.travel();
                Loc nloc = loc.adjacent(l.getDirection());
                if (this.contains(nloc)) {
                    onLight.accept(nloc);
                    newMatrix[nloc.y][nloc.x].getLight().add(l);
                    cell.postTick(this, newMatrix[nloc.y][nloc.x], l);
                } else {
                    cell.onDim(this, l);
                    l.getInformation().forEach(i -> i.onDim(l, null, this));
                }
            }
        });
        this.cells = newMatrix;
    }

    public void processCycle(Consumer<Loc> onLight) {
        this.tick();
        this.stepBoard(onLight);
    }

    public boolean hasNext() {
        boolean next = false;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (!cells[y][x].getLight().isEmpty()) {
                    next = true;
                    break;
                }
            }
        }

        return next;
    }

    public void runToCompletion(Runnable startCycle, Consumer<Loc> onLight) {
        while (hasNext()) {
            startCycle.run();
            processCycle(onLight);
        }
    }

}
