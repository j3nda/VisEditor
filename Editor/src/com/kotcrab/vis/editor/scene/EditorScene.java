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

package com.kotcrab.vis.editor.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.editor.util.BaseObservable;
import com.kotcrab.vis.runtime.scene.SceneViewport;

/**
 * Editor scene class, serialized by Kryo
 * @author Kotcrab
 */
public class EditorScene extends BaseObservable implements Disposable {
	public static final int ACTIVE_LAYER_CHANGED = 0;

	/** Scene file, path is relative to project Vis folder */
	public String path;
	public int compatibilityCode;
	public int width;
	public int height;
	public SceneViewport viewport;

	public Array<Layer> layers = new Array<>();
	private transient Layer activeLayer;

	public EditorScene (FileHandle file, int compatibilityCode, SceneViewport viewport, int width, int height) {
		this.path = file.path();
		this.compatibilityCode = compatibilityCode;
		this.viewport = viewport;
		this.width = width;
		this.height = height;

		layers.add(new Layer("Background"));
	}

	public FileHandle getFile () {
		return Gdx.files.absolute(path);
	}

	public Layer getLayerByName (String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name)) return layer;
		}

		return null;
	}

	public void setActiveLayer (Layer layer) {
		this.activeLayer = layer;
		postNotification(ACTIVE_LAYER_CHANGED);
	}

	public Layer getActiveLayer () {
		return activeLayer;
	}

	@Override
	public void dispose () {
		for (Layer layer : layers)
			layer.dispose();
	}
}
