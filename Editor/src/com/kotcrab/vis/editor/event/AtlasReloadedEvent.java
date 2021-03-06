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

package com.kotcrab.vis.editor.event;

import com.kotcrab.vis.editor.event.bus.Event;
import com.kotcrab.vis.editor.module.project.TextureCacheModule;

/**
 * Posted when {@link TextureCacheModule} has finished reloading texture atlases cache
 * @author Kotcrab
 */
public class AtlasReloadedEvent implements Event {
}
