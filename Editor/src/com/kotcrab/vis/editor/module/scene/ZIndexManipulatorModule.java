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

package com.kotcrab.vis.editor.module.scene;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.module.InjectModule;
import com.kotcrab.vis.editor.scene.EditorObject;
import com.kotcrab.vis.editor.scene.SceneSelectionRoot;
import com.kotcrab.vis.editor.util.undo.UndoableAction;
import com.kotcrab.vis.editor.util.undo.UndoableActionGroup;

/**
 * Allows to change entities z index. Z index in VisEditor is virtual and it is achieved by changing entities positions in entity list.
 * @author Kotcrab
 */
public class ZIndexManipulatorModule extends SceneModule {
	@InjectModule private UndoModule undoModule;
	@InjectModule private EntityManipulatorModule entityManipulator;

	private UndoableActionGroup actionGroup;

	private void moveSelectedEntities (boolean up) {
		actionGroup = new UndoableActionGroup();

		Array<EditorObject> selectedEntities = entityManipulator.getSelectedEntities();

		for (EditorObject entity : selectedEntities) {
			moveEntity(entity, getOverlappingEntities(entity, up), up);
		}

		actionGroup.finalizeGroup();
		undoModule.add(actionGroup);
	}

	private void moveEntity (EditorObject entity, Array<EditorObject> overlappingEntities, boolean up) {
		Array<EditorObject> entities = entityManipulator.getSelectionRoot().getSelectionEntities();

		if (overlappingEntities.size > 0) {
			int currentIndex = entities.indexOf(entity, true);
			int targetIndex = entities.indexOf(overlappingEntities.first(), true);

			for (EditorObject overlappingEntity : overlappingEntities) {
				int sceneIndex = entities.indexOf(overlappingEntity, true);
				if (up ? sceneIndex < targetIndex : sceneIndex > targetIndex)
					targetIndex = sceneIndex;
			}

			actionGroup.execute(new ZIndexChangeAction(entityManipulator.getSelectionRoot(), entity, currentIndex, targetIndex));
		}
	}

	private Array<EditorObject> getOverlappingEntities (EditorObject entity, boolean up) {
		Array<EditorObject> entities = entityManipulator.getSelectionRoot().getSelectionEntities();

		Array<EditorObject> overlapping = new Array<>();
		int entityIndex = entities.indexOf(entity, true);

		for (EditorObject sceneEntity : entities) {
			int sceneEntityIndex = entities.indexOf(sceneEntity, true);

			if (entity != sceneEntity &&
					entity.getBoundingRectangle().overlaps(sceneEntity.getBoundingRectangle())) {

				if (up ? (entityIndex < sceneEntityIndex) : (entityIndex > sceneEntityIndex))
					overlapping.add(sceneEntity);

			}
		}

		return overlapping;
	}

	@Override
	public boolean keyDown (InputEvent event, int keycode) {
		if (keycode == Keys.PAGE_UP) {
			moveSelectedEntities(true);
			return true;
		}

		if (keycode == Keys.PAGE_DOWN) {
			moveSelectedEntities(false);
			return true;
		}

		return false;
	}

	private class ZIndexChangeAction implements UndoableAction {
		private SceneSelectionRoot selectionRoot;
		private EditorObject entity;
		private int currentIndex;
		private int targetIndex;

		public ZIndexChangeAction (SceneSelectionRoot selectionRoot, EditorObject entity, int currentIndex, int targetIndex) {
			this.selectionRoot = selectionRoot;
			this.entity = entity;
			this.currentIndex = currentIndex;
			this.targetIndex = targetIndex;
		}

		@Override
		public void execute () {
			selectionRoot.getSelectionEntities().removeIndex(currentIndex);
			selectionRoot.getSelectionEntities().insert(targetIndex, entity);
		}

		@Override
		public void undo () {
			selectionRoot.getSelectionEntities().removeIndex(targetIndex);
			selectionRoot.getSelectionEntities().insert(currentIndex, entity);
		}
	}
}
