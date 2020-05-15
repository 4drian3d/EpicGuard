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

package me.ishift.epicguard.common.antibot.checks;

import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.antibot.Check;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;

public class ServerListCheck implements Check {
    private final AttackManager manager;

    public ServerListCheck(AttackManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean execute(String address, String nickname) {
        if (Configuration.serverListCheck && this.manager.isAttackMode()) {
            return !StorageManager.getStorage().getPingData().contains(address);
        }
        return false;
    }
}
