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

import java.util.List;
import java.util.function.Function;

public interface Mirrors {

    Function<Direction, Direction> HORIZONTAL_FLAT = d -> d.isVertical() ? d.flatReflection() : d.isDiagonal() ? d.reflectY() : d;
    Function<Direction, Direction> VERTICAL_FLAT = d -> d.isHorizontal() ? d.flatReflection() : d.isDiagonal() ? d.reflectX() : d;
    Function<Direction, Direction> LEFT_RIGHT_SLASH = d -> d.isDiagonal() ? (d.isUnified() ? d : d.flatReflection()) : (d.isHorizontal() ? d.withY(d.getXmod()) : d.withX(d.getYmod()));
    Function<Direction, Direction> RIGHT_LEFT_SLASH = d -> d.isDiagonal() ? (!d.isUnified() ? d : d.flatReflection()) : (d.isHorizontal() ? d.withY(-d.getXmod()) : d.withX(-d.getYmod()));
    Function<Direction, List<Direction>> LEFT_ARROW = doubled(Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.WEST, RIGHT_LEFT_SLASH, LEFT_RIGHT_SLASH);
    Function<Direction, List<Direction>> RIGHT_ARROW = doubled(Direction.WEST, Direction.SOUTH, Direction.NORTH, Direction.EAST, LEFT_RIGHT_SLASH, RIGHT_LEFT_SLASH);
    Function<Direction, List<Direction>> UP_ARROW = doubled(Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.NORTH, RIGHT_LEFT_SLASH, LEFT_RIGHT_SLASH);
    Function<Direction, List<Direction>> DOWN_ARROW = doubled(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH, LEFT_RIGHT_SLASH, RIGHT_LEFT_SLASH);

    static Function<Direction, List<Direction>> lst(Function<Direction, Direction> fnc) {
        return d -> Items.buildList(fnc.apply(d));
    }

    static Function<Direction, List<Direction>> doubled(Direction split, Direction dr1, Direction dr2, Direction consume, Function<Direction, Direction> fn1, Function<Direction, Direction> fn2) {
        return d -> {
            if(d == split) {
                return Items.buildList(fn1.apply(d), fn2.apply(d));
            } else if (d == dr1) {
                return Items.buildList(fn1.apply(d));
            } else if (d == dr2) {
                return Items.buildList(fn2.apply(d));
            } else if (d == consume) {
                return Items.buildList();
            } else {
                return Items.buildList(d);
            }
        };
    }

}
