package dev.ses.kits.manager.command;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

import java.util.UUID;

public class UUIDTypeAdapter implements TypeAdapter<UUID> {
    @Override
    public UUID convert(CommandExecutor executor, String source) throws Exception {
        return UUID.fromString(source);
    }
}
