/**
 * Copyright (C) 2013 Lucas Hart (Lukke100)
 *
 * This file is part of Sbdi
 *
 *  Sbdi is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Sbdi is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with Sbdi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lukke100.sbdi.jython;

import org.python.util.PythonInterpreter;

final public class JyInjector {
	PythonInterpreter interpreter = new PythonInterpreter();

	public JyInjector() {
		interpreter.exec("print('Jython Interpreter Initialized')");
	}

	public void exit() {
		interpreter.execfile("res/exit.py");
	};

	public void load() {

	};

	public void main() {
		interpreter.execfile("res/main.py");
	};

	public void save() {

	};

}
