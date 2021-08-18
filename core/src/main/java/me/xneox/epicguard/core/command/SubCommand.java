/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.command;

import java.util.ArrayList;
import java.util.Collection;
import me.xneox.epicguard.core.EpicGuard;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface SubCommand {
  void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard);

  default @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
    return new ArrayList<>();
  }
}