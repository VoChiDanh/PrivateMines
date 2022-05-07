package me.untouchedodin0.privatemines.mine;

import me.untouchedodin0.privatemines.PrivateMines;
import me.untouchedodin0.kotlin.mine.type.MineType;

import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class MineTypeManager {

    private final NavigableMap<String, MineType> mineTypes = new TreeMap<>();
    private final PrivateMines privateMines;

    public MineTypeManager(PrivateMines privateMines) {
        this.privateMines = privateMines;
    }

    public void registerMineType(MineType mineType) {
        if (mineType == null) {
            privateMines.getLogger().info("MineType was null!");
        }
        if (mineType != null) {
            privateMines.getLogger().info("Registering mine type: " + mineType.getName());
            mineTypes.put(mineType.getName(), mineType);
        }
    }

    public MineType getMineType(MineType name) {
        return mineTypes.get(name.getName());
    }

    public MineType getMineType(String string) {
        return mineTypes.get(string);
    }

    public MineType getDefaultMineType() {
        if (mineTypes.isEmpty()) {
            privateMines.getLogger().info("No default mine type was found!\n" +
                                                  "Create a mine type in the mineTypes section of the config.yml" +
                                                  "Please ask in the discord server if you need help");
            throw new RuntimeException();
        }
        return mineTypes.firstEntry().getValue();
    }

    public void clear() {
        mineTypes.clear();
    }

    public boolean isLastMineType(MineType mineType) {
        return mineTypes.lastEntry().getValue().equals(mineType);
    }

    public MineType getNextMineType(MineType mineType) {
        return Optional.ofNullable(mineTypes.higherEntry(mineType.getName()))
                .orElse(mineTypes.lastEntry())
                .getValue();
    }

    public MineType getNextMineType(String name) {
        return Optional.ofNullable(mineTypes.higherEntry(name))
                .orElse(mineTypes.lastEntry())
                .getValue();
    }

    public NavigableMap<String, MineType> getMineTypes() {
        return mineTypes;
    }

    public int getTotalMineTypes() {
        return mineTypes.size();
    }
}