import arc.Events;
import arc.struct.ObjectMap;
import arc.util.Timer;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.mod.Plugin;

public class BoostPlayers extends Plugin {
    private static Timer.Task t;
    private static ObjectMap<String, Unit> lastUnits = new ObjectMap<>();

    @Override
    public void init() {
        if (t != null) t.cancel();
        t = Timer.schedule(() -> Groups.player.each(p -> boost(p.unit())), 0f, 60f);
        Events.on(EventType.PlayerJoin.class, e -> {
            boost(e.player.unit());
            lastUnits.put(e.player.uuid(), e.player.unit());
        });
        Events.on(EventType.PlayEvent.class, e -> {
            lastUnits.clear();
            Groups.player.each(p -> {
                boost(p.unit());
                lastUnits.put(p.uuid(), p.unit());
            });
        });
        Events.on(EventType.UnitChangeEvent.class, e -> {
            if (e.player == null) return;
            Unit prev = lastUnits.get(e.player.uuid());
            if (prev != null && prev != e.unit) unboost(prev);
            boost(e.unit);
            lastUnits.put(e.player.uuid(), e.unit);
        });
        Events.on(EventType.PlayerLeave.class, e -> {
            unboost(e.player.unit());
            lastUnits.remove(e.player.uuid());
        });
    }
    private static void boost(Unit u) { if (u != null && u.isPlayer()) u.apply(StatusEffects.overdrive, 62f); }
    private static void unboost(Unit u) { if (u != null) u.unapply(StatusEffects.overdrive); }
}
