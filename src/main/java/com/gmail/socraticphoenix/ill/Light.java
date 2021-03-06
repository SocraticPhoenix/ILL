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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Light {
    private double initialIntensity;
    private int distanceTraveled;
    private Direction direction;
    private Map<String, Information> informationMap;

    public Light(double initialIntensity, Direction direction) {
        this.initialIntensity = initialIntensity;
        this.distanceTraveled = 0;
        this.direction = direction;
        this.informationMap = new HashMap<>();
    }

    public Light copy() {
        Light light = new Light(this.initialIntensity, this.direction);
        light.distanceTraveled = this.distanceTraveled;
        this.informationMap.forEach((key, val) -> light.informationMap.put(key, val.copy()));
        return light;
    }

    public void setInitialIntensity(double initialIntensity) {
        this.initialIntensity = initialIntensity;
    }

    public void setDistanceTraveled(int distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public double getInitialIntensity() {
        return this.initialIntensity;
    }

    public int getDistanceTraveled() {
        return this.distanceTraveled;
    }

    public void applyInformation(String key, Information information) {
        this.informationMap.put(key, information);
    }

    public void setIntensity(double quantity) {
        this.initialIntensity = quantity;
        this.distanceTraveled = 1;
    }

    public void travel() {
        this.distanceTraveled++;
    }

    public boolean hasInformation(String key) {
        return this.informationMap.containsKey(key);
    }

    public Collection<Information> getInformation() {
        return this.informationMap.values();
    }

    public <T extends Information> T getInformation(String key) {
        return (T) this.informationMap.get(key);
    }

    public double getIntensity() {
        return this.initialIntensity / this.distanceTraveled;
    }

    public int getFloorIntensity() {
        return (int) this.getIntensity();
    }

    public int getCeilIntensity() {
        return (int) Math.ceil(this.getIntensity());
    }

    public int getRoundIntensity() {
        return (int) Math.round(this.getIntensity());
    }

    public boolean hasDimmed() {
        return this.getRoundIntensity() == 0;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
