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
package com.gmail.socraticphoenix.ill.instructions;

import com.gmail.socraticphoenix.ill.Cell;
import com.gmail.socraticphoenix.ill.Information;
import com.gmail.socraticphoenix.ill.Light;
import com.gmail.socraticphoenix.ill.ProgramMatrix;

public class CarryThrough extends AbstractInstruction {

    public CarryThrough() {
        super('T');
    }

    @Override
    public void tickImpl(Light light, Cell cell, ProgramMatrix matrix) {
        light.applyInformation(CarryThroughInfo.class.getName(), new CarryThroughInfo(false, light.getDistanceTraveled(), light.getInitialIntensity()));
    }

    @Override
    public void postTickImpl(Light light, Cell cell, ProgramMatrix matrix) {
        if(light.hasInformation(CarryThroughInfo.class.getName())) {
            CarryThroughInfo info = light.getInformation(CarryThroughInfo.class.getName());
            info.setExpired(true);
            light.setDistanceTraveled(info.getTravelState());
            light.setInitialIntensity(info.getIntensityState());
        }
    }

    @Override
    public void dimImpl(Light light, ProgramMatrix matrix) {

    }

    private static class CarryThroughInfo implements Information {
        private boolean expired;
        private int travelState;
        private double intensityState;

        public CarryThroughInfo(boolean expired, int travelState, double intensityState) {
            this.expired = expired;
            this.travelState = travelState;
            this.intensityState = intensityState;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public void setTravelState(int travelState) {
            this.travelState = travelState;
        }

        public void setIntensityState(double intensityState) {
            this.intensityState = intensityState;
        }

        public boolean isExpired() {
            return this.expired;
        }

        public int getTravelState() {
            return this.travelState;
        }

        public double getIntensityState() {
            return this.intensityState;
        }

        @Override
        public boolean isExpired(Light light, Cell cell, ProgramMatrix matrix) {
            return this.expired;
        }

        @Override
        public void onExpiration(Light light, Cell cell, ProgramMatrix matrix) {

        }

        @Override
        public void tick(Light light, Cell cell, ProgramMatrix matrix) {

        }

        @Override
        public void postTick(Light light, Cell cell, ProgramMatrix matrix) {

        }

        @Override
        public void onDim(Light light, Cell cell, ProgramMatrix matrix) {

        }

        @Override
        public Information copy() {
            return new CarryThroughInfo(this.expired, this.travelState, this.intensityState);
        }

    }

}
