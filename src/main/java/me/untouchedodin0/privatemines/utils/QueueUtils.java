package me.untouchedodin0.privatemines.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.untouchedodin0.kotlin.mine.storage.MineStorage;
import me.untouchedodin0.kotlin.mine.type.MineType;
import me.untouchedodin0.privatemines.PrivateMines;
import me.untouchedodin0.privatemines.factory.MineFactory;
import me.untouchedodin0.privatemines.mine.MineTypeManager;
import me.untouchedodin0.privatemines.utils.world.MineWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import redempt.redlib.misc.Task;

public class QueueUtils {

  PrivateMines privateMines = PrivateMines.getPrivateMines();
  MineTypeManager mineTypeManager = privateMines.getMineTypeManager();

  public Queue<UUID> queue = new LinkedList<>();
  public List<UUID> waitingInQueue = new ArrayList<>();

  public Queue<UUID> getQueue() {
    return queue;
  }

  public void add(UUID uuid) {
    if (!queue.contains(uuid)) {
      queue.add(uuid);
    }
  }

  public void claim(UUID uuid) {
    if (waitingInQueue.contains(uuid)) return;
    add(uuid);
  }

  public boolean isInQueue(UUID uuid) {
    return waitingInQueue.contains(uuid);
  }

  public void claim(Player player) {
    MineStorage mineStorage = privateMines.getMineStorage();
    MineFactory mineFactory = new MineFactory();
    MineWorldManager mineWorldManager = privateMines.getMineWorldManager();
    Location location = mineWorldManager.getNextFreeLocation();
    mineWorldManager.setCurrentLocation(location);
    MineType defaultMineType = mineTypeManager.getDefaultMineType();

    if (mineStorage.hasMine(player)) {
      player.sendMessage(ChatColor.RED + "You already own a mine!");
      return;
    }

    for (int i = 0; i < 15; i++) {
      queue.add(UUID.randomUUID());
    }
    if (queue.contains(player.getUniqueId())) return;
    claim(player.getUniqueId());

    Task.syncRepeating(() -> {
      AtomicInteger slot = new AtomicInteger(1);
      List<UUID> uuidList = queue.stream().toList();

      for (UUID uuid : uuidList) {
        if (!uuid.equals(player.getUniqueId())) {
          slot.incrementAndGet();
        } else {
          player.sendMessage(ChatColor.GREEN + "You're at slot #" + slot.get());
        }
      }
    }, 0L, 60L);

    Task.syncRepeating(() -> {
      UUID poll = queue.poll();
      if (poll == null) return;
      Bukkit.broadcastMessage("poll " + poll);
      if (poll.equals(player.getUniqueId())) {
        player.sendMessage(ChatColor.GREEN + "Creating your mine.....");
        mineFactory.create(player, location, defaultMineType, true);
      }
    }, 0L, 120L);
  }
}