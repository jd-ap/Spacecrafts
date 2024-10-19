package travel.w2m.techproof.web;

import travel.w2m.techproof.entity.Spacecraft;
import travel.w2m.techproof.model.SpacecraftCommandDto;
import travel.w2m.techproof.model.SpacecraftDto;

public class SpacecraftMapper {

    public static Spacecraft requestBodyToEntity(SpacecraftCommandDto spacecraftDto) {
        return new Spacecraft();
    }

    public static SpacecraftDto entityToResponseBody(Spacecraft spacecraft) {
        return new SpacecraftDto();
    }
}
