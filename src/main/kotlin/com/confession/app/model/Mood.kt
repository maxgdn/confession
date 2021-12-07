package com.confession.app.model

enum class Mood(quadrant: MoodQuadrant) {
    ENRAGED(quadrant = MoodQuadrant.RED),
    PANICKED(quadrant = MoodQuadrant.RED),
    STRESSED(quadrant = MoodQuadrant.RED),
    JITTERY(quadrant = MoodQuadrant.RED),
    SHOCKED(quadrant = MoodQuadrant.RED),

    LIVID(quadrant = MoodQuadrant.RED),
    FURIOUS(quadrant = MoodQuadrant.RED),
    FRUSTRATED(quadrant = MoodQuadrant.RED),
    TENSE(quadrant = MoodQuadrant.RED),
    STUNNED(quadrant = MoodQuadrant.RED),

    FUMING(quadrant = MoodQuadrant.RED),
    FRIGHTENED(quadrant = MoodQuadrant.RED),
    ANGRY(quadrant = MoodQuadrant.RED),
    NERVOUS(quadrant = MoodQuadrant.RED),
    RESTLESS(quadrant = MoodQuadrant.RED),

    ANXIOUS(quadrant = MoodQuadrant.RED),
    APPREHENSIVE(quadrant = MoodQuadrant.RED),
    WORRIED(quadrant = MoodQuadrant.RED),
    IRRITATED(quadrant = MoodQuadrant.RED),
    ANNOYED(quadrant = MoodQuadrant.RED),

    REPULSED(quadrant = MoodQuadrant.RED),
    TROUBLED(quadrant = MoodQuadrant.RED),
    CONCERNED(quadrant = MoodQuadrant.RED),
    UNEASY(quadrant = MoodQuadrant.RED),
    PEEVED(quadrant = MoodQuadrant.RED),

    SURPRISED(quadrant = MoodQuadrant.YELLOW),
    UPBEAT(quadrant = MoodQuadrant.YELLOW),
    FESTIVE(quadrant = MoodQuadrant.YELLOW),
    EXHILARATED(quadrant = MoodQuadrant.YELLOW),
    ECSTATIC(quadrant = MoodQuadrant.YELLOW),

    HYPER(quadrant = MoodQuadrant.YELLOW),
    CHEERFUL(quadrant = MoodQuadrant.YELLOW),
    MOTIVATED(quadrant = MoodQuadrant.YELLOW),
    INSPIRED(quadrant = MoodQuadrant.YELLOW),
    ELATED(quadrant = MoodQuadrant.YELLOW),

    ENERGIZED(quadrant = MoodQuadrant.YELLOW),
    LIVELY(quadrant = MoodQuadrant.YELLOW),
    ENTHUSIASTIC(quadrant = MoodQuadrant.YELLOW),
    OPTIMISTIC(quadrant = MoodQuadrant.YELLOW),
    EXCITED(quadrant = MoodQuadrant.YELLOW),

    PLEASED(quadrant = MoodQuadrant.YELLOW),
    HAPPY(quadrant = MoodQuadrant.YELLOW),
    FOCUSED(quadrant = MoodQuadrant.YELLOW),
    PROUD(quadrant = MoodQuadrant.YELLOW),
    THRILLED(quadrant = MoodQuadrant.YELLOW),

    PLEASANT(quadrant = MoodQuadrant.YELLOW),
    JOYFUL(quadrant = MoodQuadrant.YELLOW),
    HOPEFUL(quadrant = MoodQuadrant.YELLOW),
    PLAYFUL(quadrant = MoodQuadrant.YELLOW),
    BLISSFUL(quadrant = MoodQuadrant.YELLOW),

    DISGUSTED(quadrant = MoodQuadrant.BLUE),
    GLUM(quadrant = MoodQuadrant.BLUE),
    DISAPPOINTED(quadrant = MoodQuadrant.BLUE),
    DOWN(quadrant = MoodQuadrant.BLUE),
    APATHETIC(quadrant = MoodQuadrant.BLUE),

    PESSIMISTIC(quadrant = MoodQuadrant.BLUE),
    MOROSE(quadrant = MoodQuadrant.BLUE),
    DISCOURAGED(quadrant = MoodQuadrant.BLUE),
    SAD(quadrant = MoodQuadrant.BLUE),
    BORED(quadrant = MoodQuadrant.BLUE),

    ALIENATED(quadrant = MoodQuadrant.BLUE),
    MISERABLE(quadrant = MoodQuadrant.BLUE),
    LONELY(quadrant = MoodQuadrant.BLUE),
    DISHEARTENED(quadrant = MoodQuadrant.BLUE),
    TIRED(quadrant = MoodQuadrant.BLUE),

    DESPONDENT(quadrant = MoodQuadrant.BLUE),
    DEPRESSED(quadrant = MoodQuadrant.BLUE),
    SULLEN(quadrant = MoodQuadrant.BLUE),
    EXHAUSTED(quadrant = MoodQuadrant.BLUE),
    FATIGUED(quadrant = MoodQuadrant.BLUE),

    DESPAIR(quadrant = MoodQuadrant.BLUE),
    HOPELESS(quadrant = MoodQuadrant.BLUE),
    DESOLATE(quadrant = MoodQuadrant.BLUE),
    SPENT(quadrant = MoodQuadrant.BLUE),
    DRAINED(quadrant = MoodQuadrant.BLUE),

    AT_EASE(quadrant = MoodQuadrant.GREEN),
    EASYGOING(quadrant = MoodQuadrant.GREEN),
    CONTENT(quadrant = MoodQuadrant.GREEN),
    LOVING(quadrant = MoodQuadrant.GREEN),
    FULFILLED(quadrant = MoodQuadrant.GREEN),

    CALM(quadrant = MoodQuadrant.GREEN),
    SECURE(quadrant = MoodQuadrant.GREEN),
    SATISFIED(quadrant = MoodQuadrant.GREEN),
    GRATEFUL(quadrant = MoodQuadrant.GREEN),
    TOUCHED(quadrant = MoodQuadrant.GREEN),

    RELAXED(quadrant = MoodQuadrant.GREEN),
    CHILL(quadrant = MoodQuadrant.GREEN),
    RESTFUL(quadrant = MoodQuadrant.GREEN),
    BLESSED(quadrant = MoodQuadrant.GREEN),
    BALANCED(quadrant = MoodQuadrant.GREEN),

    MELLOW(quadrant = MoodQuadrant.GREEN),
    THOUGHTFUL(quadrant = MoodQuadrant.GREEN),
    PEACEFUL(quadrant = MoodQuadrant.GREEN),
    COMFY(quadrant = MoodQuadrant.GREEN),
    CAREFREE(quadrant = MoodQuadrant.GREEN),

    SLEEPY(quadrant = MoodQuadrant.GREEN),
    COMPLACENT(quadrant = MoodQuadrant.GREEN),
    TRANQUIL(quadrant = MoodQuadrant.GREEN),
    COZY(quadrant = MoodQuadrant.GREEN),
    SERENE(quadrant = MoodQuadrant.GREEN),

}