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

package com.kotcrab.vis.runtime.data;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.annotation.OverrideCallSuper;
import com.kotcrab.vis.runtime.assets.VisAssetDescriptor;
import com.kotcrab.vis.runtime.entity.EntityGroup;

/**
 * Data class for {@link EntityGroup}
 * @author Kotcrab
 */
public class EntityGroupData extends EntityData<EntityGroup> {
	public Array<EntityData> entities = new Array<EntityData>();

	public EntityGroupData () {
	}

	public EntityGroupData (String id) {
		this.id = id;
	}

	@Override
	@OverrideCallSuper
	public void saveFrom (EntityGroup entity, VisAssetDescriptor assetDescriptor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void loadTo (EntityGroup entity) {
		throw new UnsupportedOperationException();
	}
}
