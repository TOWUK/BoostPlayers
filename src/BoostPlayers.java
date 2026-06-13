import arc.Events;
import arc.util.Timer;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.mod.Plugin;
public class BoostPlayers extends Plugin {
    private static Timer.Task t;
    @Override
    public void init() {
        if (t != null) t.cancel();
        t = Timer.schedule(() -> Groups.player.each(p -> boost(p.unit())), 0f, 60f);
        Events.on(EventType.PlayerJoin.class, e -> boost(e.player.unit()));
        Events.on(EventType.PlayEvent.class, e -> Groups.player.each(p -> boost(p.unit())));
        Events.on(EventType.UnitChangeEvent.class, e -> boost(e.unit));
    }
    private static void boost(Unit u) { if (u != null && u.isPlayer()) u.apply(StatusEffects.overdrive, 62f); }
}
