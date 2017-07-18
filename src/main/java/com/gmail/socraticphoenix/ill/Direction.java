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

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTH_EAST(1, -1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(-1, 1);

    private int xmod;
    private int ymod;

    Direction(int xmod, int ymod) {
        this.xmod = xmod;
        this.ymod = ymod;
    }

    public int getXmod() {
        return this.xmod;
    }

    public int getYmod() {
        return this.ymod;
    }

    public boolean isHorizontal() {
        return this.ymod == 0;
    }

    public boolean isVertical() {
        return this.xmod == 0;
    }

    public boolean isDiagonal() {
        return this.ymod != 0 && this.xmod != 0;
    }

    public boolean isLeftRight() {
        return this.xmod < 0;
    }

    public boolean isRightLeft() {
        return this.xmod > 0;
    }

    public boolean isUpDown() {
        return this.ymod > 0;
    }

    public boolean isDownUp() {
        return this.ymod < 0;
    }

    public boolean isUnified() {
        return this.ymod == this.xmod;
    }

    public Direction withX(int x) {
        return from(0, x);
    }

    public Direction withY(int y) {
        return from(y, 0);
    }

    public Direction reflectX() {
        return from(ymod, -xmod);
    }

    public Direction reflectY() {
        return from(-ymod, xmod);
    }

    public Direction swapped() {
        return from(xmod, ymod);
    }

    public Direction flatReflection() {
        return from(-ymod, -xmod);
    }

    public static Direction from(int ymod, int xmod) {
        for(Direction d : values()) {
            if(d.getYmod() == ymod && d.getXmod() == xmod) {
                return d;
            }
        }

        return null;
    }

}
