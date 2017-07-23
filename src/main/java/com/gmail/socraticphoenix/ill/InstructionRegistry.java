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

import com.gmail.socraticphoenix.ill.instructions.CarryThrough;
import com.gmail.socraticphoenix.ill.instructions.CharInput;
import com.gmail.socraticphoenix.ill.instructions.CharOutput;
import com.gmail.socraticphoenix.ill.instructions.GatherChar;
import com.gmail.socraticphoenix.ill.instructions.Mirror;
import com.gmail.socraticphoenix.ill.instructions.NewLine;
import com.gmail.socraticphoenix.ill.instructions.NumericalInput;
import com.gmail.socraticphoenix.ill.instructions.NumericalOutput;
import com.gmail.socraticphoenix.ill.instructions.ReIntensify;
import com.gmail.socraticphoenix.ill.instructions.SetIntensity;

import java.util.LinkedHashMap;
import java.util.Map;

public class InstructionRegistry {
    private static Map<Integer, Instruction> instructions = new LinkedHashMap<>();

    static {
        registerDefaults();
    }

    public static void registerDefaults() {
        for (int i = 0; i <= 9; i++) {
            register(new SetIntensity(i));
        }

        register(new NumericalInput());
        register(new NumericalOutput());
        register(new ReIntensify());
        register(new CharInput());
        register(new CharOutput());
        register(new GatherChar());
        register(new CarryThrough());
        register(new NewLine());

        register(new Mirror('\\', Mirrors.lst(Mirrors.LEFT_RIGHT_SLASH)));
        register(new Mirror('/', Mirrors.lst(Mirrors.RIGHT_LEFT_SLASH)));
        register(new Mirror('-', Mirrors.lst(Mirrors.HORIZONTAL_FLAT)));
        register(new Mirror('|', Mirrors.lst(Mirrors.VERTICAL_FLAT)));
        register(new Mirror('>', Mirrors.RIGHT_ARROW));
        register(new Mirror('<', Mirrors.LEFT_ARROW));
        register(new Mirror('^', Mirrors.UP_ARROW));
        register(new Mirror('v', Mirrors.DOWN_ARROW));
    }

    public static void register(Instruction instruction) {
        if(instructions.containsKey(instruction.id())) {
            throw new IllegalArgumentException("Duplicated id: " + new String(new int[]{instruction.id()}, 0, 1));
        }
        instructions.put(instruction.id(), instruction);
    }

    public static Instruction getInstruction(int key) {
        return instructions.get(key);
    }

}
