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

package com.kotcrab.vis.editor.module.project;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kotcrab.vis.editor.App;
import com.kotcrab.vis.editor.event.ParticleReloadedEvent;
import com.kotcrab.vis.editor.module.InjectModule;
import com.kotcrab.vis.editor.util.DirectoryWatcher.WatchListener;
import com.kotcrab.vis.runtime.assets.PathAsset;
import com.kotcrab.vis.runtime.assets.VisAssetDescriptor;
import com.kotcrab.vis.runtime.util.UnsupportedAssetDescriptorException;

/**
 * Allows to get loaded particles from project asset directory.
 * @author Kotcrab
 */
public class ParticleCacheModule extends ProjectModule implements WatchListener {
	@InjectModule private FileAccessModule fileAccess;
	@InjectModule private AssetsWatcherModule watcherModule;

	@Override
	public void init () {
		watcherModule.addListener(this);
	}

	public ParticleEffect get (VisAssetDescriptor assetDescriptor) {
		if (assetDescriptor instanceof PathAsset == false)
			throw new UnsupportedAssetDescriptorException(assetDescriptor);

		PathAsset path = (PathAsset) assetDescriptor;
		return get(fileAccess.getAssetsFolder().child(path.getPath()));
	}

	private ParticleEffect get (FileHandle file) {
		ParticleEffect effect = new ParticleEffect();
		effect.load(file, file.parent());
		return effect;
	}

	@Override
	public void dispose () {
		watcherModule.removeListener(this);
	}

	@Override
	public void fileChanged (FileHandle file) {
		App.eventBus.post(new ParticleReloadedEvent());
	}

	@Override
	public void fileDeleted (FileHandle file) {
	}

	@Override
	public void fileCreated (FileHandle file) {
	}
}
