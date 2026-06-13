import arc.util.Timer;
import mindustry.content.StatusEffects;
import mindustry.gen.Groups;
import mindustry.mod.Plugin;
public class BoostPlayers extends Plugin {
    @Override
    public void init() {
        Timer.schedule(() -> Groups.player.each(p -> { var u = p.unit(); if (u != null) { u.apply(StatusEffects.overdrive, 2f); } }), 0f, 1f);
    }
}
