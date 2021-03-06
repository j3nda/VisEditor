/*
 * Spine Runtimes Software License
 * Version 2.3
 *
 * Copyright (c) 2013-2015, Esoteric Software
 * All rights reserved.
 *
 * You are granted a perpetual, non-exclusive, non-sublicensable and
 * non-transferable license to use, install, execute and perform the Spine
 * Runtimes Software (the "Software") and derivative works solely for personal
 * or internal use. Without the written permission of Esoteric Software (see
 * Section 2 of the Spine Software License Agreement), you may not (a) modify,
 * translate, adapt or otherwise create derivative works, improvements of the
 * Software or develop new applications using the Software or (b) remove,
 * delete, alter or obscure any trademarks or any copyright, trademark, patent
 * or other intellectual property or proprietary rights notices on or in the
 * Software, including any copy thereof. Redistributions in binary or source
 * form must include this license and terms.
 *
 * THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL ESOTERIC SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.kotcrab.vis.plugin.spine.runtime;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.kotcrab.vis.plugin.spine.runtime.SkeletonDataLoader.SkeletonDataLoaderParameter;
import com.kotcrab.vis.runtime.plugin.EntitySupport;
import com.kotcrab.vis.runtime.plugin.VisPlugin;

@VisPlugin
public class SpineSupport implements EntitySupport<SpineData, SpineEntity> {
	private SkeletonRenderer skeletonRenderer;

	public SpineSupport () {
		skeletonRenderer = new SkeletonRenderer();
	}

	@Override
	public void setLoaders (AssetManager manager) {
		manager.setLoader(SkeletonData.class, new SkeletonDataLoader());
	}

	@Override
	public Class<SpineData> getEntityDataClass () {
		return SpineData.class;
	}

	@Override
	public void resolveDependencies (Array<AssetDescriptor> deps, SpineData entityData) {
		SpineAssetDescriptor asset = (SpineAssetDescriptor) entityData.assetDescriptor;
		SkeletonDataLoaderParameter parameter = new SkeletonDataLoaderParameter(asset.getAtlasPath(), asset.getScale());
		deps.add(new AssetDescriptor(asset.getSkeletonPath(), SkeletonData.class, parameter));
	}

	@Override
	public SpineEntity getInstanceFromData (AssetManager manager, SpineData data) {
		SpineAssetDescriptor asset = (SpineAssetDescriptor) data.assetDescriptor;
		SkeletonData skeletonData = manager.get(asset.getSkeletonPath(), SkeletonData.class);
		SpineEntity entity = new SpineEntity(data.id, skeletonData, skeletonRenderer);
		data.loadTo(entity);
		return entity;
	}
}
