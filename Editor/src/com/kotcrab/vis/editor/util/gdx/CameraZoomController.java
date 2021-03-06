/*
 * Copyright 2014-2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.vis.editor.util.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.kotcrab.vis.editor.module.ModuleInput;

/**
 * Camera controller that supports zooming around point.
 * @author Kotcrab
 */
public class CameraZoomController {
	private Vector3 unprojectVec;
	private final OrthographicCamera camera;

	public CameraZoomController (OrthographicCamera camera, Vector3 unprojectVec) {
		this.camera = camera;
		this.unprojectVec = unprojectVec;
	}

	/**
	 * Zooms camera around given point
	 * @param x screen x
	 * @param y screen y
	 * @param amount from {@link ModuleInput#scrolled(InputEvent, float, float, int)}
	 */
	public boolean zoomAroundPoint (float x, float y, int amount) {
		float newZoom = 0;
		camera.unproject(unprojectVec.set(x, y, 0));
		float cursorX = unprojectVec.x;
		float cursorY = unprojectVec.y;

		if (amount == -1) { // zoom in
			if (camera.zoom <= 0.3f) return false;
			newZoom = camera.zoom - 0.1f * camera.zoom * 2;

		}

		if (amount == 1) { // zoom out
			if (camera.zoom >= 10f) return false;
			newZoom = camera.zoom + 0.1f * camera.zoom * 2;
		}

		// some complicated calculations, basically we want to zoom in/out where mouse pointer is
		camera.position.x = cursorX + (newZoom / camera.zoom) * (camera.position.x - cursorX);
		camera.position.y = cursorY + (newZoom / camera.zoom) * (camera.position.y - cursorY);
		camera.zoom = newZoom;

		return true;
	}
}
