package strategyLoop.dagannoth;

import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.Tile;

import dagannoths.MavenDagannoths;

public class Abort implements Actions {

	Tile[] varTiles = { new Tile(3205, 3437), new Tile(3205, 3438),
			new Tile(3205, 3439), new Tile(3204, 3440), new Tile(3204, 3439),
			new Tile(3205, 3436), new Tile(3205, 3435), new Tile(3205, 3434),
			new Tile(3205, 3433), new Tile(3205, 3432), new Tile(3205, 3431),
			new Tile(3205, 3430), new Tile(3205, 3429), new Tile(3205, 3428),
			new Tile(3206, 3427), new Tile(3207, 3426), new Tile(3207, 3425),
			new Tile(3207, 3424), new Tile(3206, 3424), new Tile(3205, 3424),
			new Tile(3204, 3424), new Tile(3204, 3425), new Tile(3204, 3426),
			new Tile(3208, 3424), new Tile(3208, 3423), new Tile(3208, 3422),
			new Tile(3209, 3421), new Tile(3210, 3421), new Tile(3211, 3421),
			new Tile(3212, 3421), new Tile(3213, 3421), new Tile(3214, 3421),
			new Tile(3215, 3421), new Tile(3216, 3422), new Tile(3217, 3423),
			new Tile(3218, 3423), new Tile(3219, 3422), new Tile(3220, 3422),
			new Tile(3221, 3421), new Tile(3221, 3420), new Tile(3221, 3419),
			new Tile(3222, 3418), new Tile(3223, 3418), new Tile(3224, 3418),
			new Tile(3225, 3418), new Tile(3225, 3419), new Tile(3225, 3420),
			new Tile(3225, 3421), new Tile(3225, 3422), new Tile(3225, 3423),
			new Tile(3225, 3424), new Tile(3226, 3425), new Tile(3227, 3425),
			new Tile(3228, 3425), new Tile(3229, 3425), new Tile(3229, 3426),
			new Tile(3229, 3427), new Tile(3229, 3428), new Tile(3230, 3428),
			new Tile(3231, 3428), new Tile(3232, 3428), new Tile(3233, 3428),
			new Tile(3234, 3428), new Tile(3235, 3428), new Tile(3236, 3428),
			new Tile(3237, 3428), new Tile(3238, 3428), new Tile(3239, 3428),
			new Tile(3240, 3428), new Tile(3241, 3428), new Tile(3242, 3428),
			new Tile(3242, 3427), new Tile(3242, 3426), new Tile(3242, 3425),
			new Tile(3242, 3424), new Tile(3242, 3423), new Tile(3242, 3422),
			new Tile(3242, 3421), new Tile(3242, 3420), new Tile(3242, 3419),
			new Tile(3242, 3418), new Tile(3243, 3417), new Tile(3244, 3416),
			new Tile(3245, 3415), new Tile(3246, 3414), new Tile(3247, 3413),
			new Tile(3248, 3413), new Tile(3249, 3413), new Tile(3250, 3413),
			new Tile(3251, 3413), new Tile(3252, 3413), new Tile(3253, 3413),
			new Tile(3254, 3413), new Tile(3255, 3413), new Tile(3256, 3413),
			new Tile(3257, 3413), new Tile(3258, 3413), new Tile(3259, 3413),
			new Tile(3260, 3413), new Tile(3260, 3414), new Tile(3261, 3414),
			new Tile(3262, 3414), new Tile(3263, 3414), new Tile(3264, 3415),
			new Tile(3265, 3416), new Tile(3266, 3417), new Tile(3267, 3418),
			new Tile(3268, 3419), new Tile(3269, 3420), new Tile(3270, 3421),
			new Tile(3271, 3422), new Tile(3271, 3423), new Tile(3271, 3424),
			new Tile(3271, 3425), new Tile(3271, 3426), new Tile(3271, 3427),
			new Tile(3271, 3428), new Tile(3272, 3428), new Tile(3273, 3428),
			new Tile(3274, 3428), new Tile(3275, 3428), new Tile(3276, 3428),
			new Tile(3277, 3427), new Tile(3277, 3426), new Tile(3276, 3425),
			new Tile(3275, 3424), new Tile(3276, 3424), new Tile(3277, 3424),
			new Tile(3278, 3425), new Tile(3279, 3426), new Tile(3280, 3427),
			new Tile(3281, 3427), new Tile(3282, 3427), new Tile(3283, 3427),
			new Tile(3284, 3427), new Tile(3285, 3427), new Tile(3286, 3427),
			new Tile(3287, 3427), new Tile(3288, 3427), new Tile(3288, 3428),
			new Tile(3288, 3429), new Tile(3288, 3430), new Tile(3288, 3431),
			new Tile(3288, 3432), new Tile(3288, 3433), new Tile(3288, 3434),
			new Tile(3288, 3435), new Tile(3288, 3436), new Tile(3288, 3437),
			new Tile(3288, 3438), new Tile(3289, 3439), new Tile(3290, 3440),
			new Tile(3290, 3441), new Tile(3290, 3442), new Tile(3291, 3443),
			new Tile(3291, 3444), new Tile(3291, 3445), new Tile(3291, 3446),
			new Tile(3291, 3447), new Tile(3291, 3448), new Tile(3292, 3449),
			new Tile(3292, 3450), new Tile(3292, 3451), new Tile(3292, 3452),
			new Tile(3292, 3453), new Tile(3292, 3454), new Tile(3292, 3455),
			new Tile(3292, 3456), new Tile(3292, 3457), new Tile(3292, 3458),
			new Tile(3292, 3459), new Tile(3292, 3460), new Tile(3292, 3461),
			new Tile(3292, 3462), new Tile(3292, 3463), new Tile(3292, 3464),
			new Tile(3292, 3465), new Tile(3292, 3466), new Tile(3292, 3467),
			new Tile(3292, 3468), new Tile(3293, 3469), new Tile(3293, 3470),
			new Tile(3293, 3471), new Tile(3293, 3472), new Tile(3293, 3473),
			new Tile(3293, 3474), new Tile(3293, 3475), new Tile(3293, 3476),
			new Tile(3292, 3476), new Tile(3291, 3476), new Tile(3290, 3476),
			new Tile(3289, 3477), new Tile(3288, 3477), new Tile(3287, 3477),
			new Tile(3286, 3477), new Tile(3286, 3476), new Tile(3286, 3475),
			new Tile(3286, 3474), new Tile(3286, 3473), new Tile(3285, 3472),
			new Tile(3284, 3472), new Tile(3283, 3472), new Tile(3282, 3472),
			new Tile(3282, 3473), new Tile(3281, 3474), new Tile(3280, 3474),
			new Tile(3279, 3474), new Tile(3278, 3474), new Tile(3277, 3474),
			new Tile(3276, 3475), new Tile(3275, 3475), new Tile(3274, 3475),
			new Tile(3276, 3474), new Tile(3276, 3473), new Tile(3276, 3472),
			new Tile(3275, 3472), new Tile(3275, 3471), new Tile(3275, 3470),
			new Tile(3275, 3469), new Tile(3275, 3468), new Tile(3275, 3467),
			new Tile(3275, 3466), new Tile(3275, 3465), new Tile(3275, 3464),
			new Tile(3275, 3463), new Tile(3275, 3462), new Tile(3274, 3461),
			new Tile(3273, 3460), new Tile(3274, 3460), new Tile(3275, 3459),
			new Tile(3276, 3458), new Tile(3277, 3457), new Tile(3278, 3456),
			new Tile(3279, 3455), new Tile(3280, 3454), new Tile(3280, 3453),
			new Tile(3281, 3452), new Tile(3281, 3451), new Tile(3281, 3450),
			new Tile(3281, 3449), new Tile(3281, 3448), new Tile(3281, 3447),
			new Tile(3281, 3446), new Tile(3280, 3446), new Tile(3279, 3446),
			new Tile(3278, 3446), new Tile(3277, 3446), new Tile(3277, 3445),
			new Tile(3277, 3444), new Tile(3276, 3443), new Tile(3275, 3442),
			new Tile(3275, 3441), new Tile(3275, 3440), new Tile(3275, 3439),
			new Tile(3275, 3438), new Tile(3275, 3437), new Tile(3275, 3436),
			new Tile(3275, 3435), new Tile(3275, 3434), new Tile(3275, 3433),
			new Tile(3275, 3432), new Tile(3275, 3431), new Tile(3275, 3430),
			new Tile(3274, 3429), new Tile(3273, 3429), new Tile(3272, 3429),
			new Tile(3271, 3429), new Tile(3271, 3430), new Tile(3271, 3431),
			new Tile(3271, 3432), new Tile(3271, 3433), new Tile(3271, 3434),
			new Tile(3270, 3434), new Tile(3269, 3434), new Tile(3268, 3434),
			new Tile(3267, 3435), new Tile(3266, 3436), new Tile(3266, 3437),
			new Tile(3265, 3437), new Tile(3264, 3437), new Tile(3263, 3437),
			new Tile(3262, 3437), new Tile(3261, 3438), new Tile(3260, 3438),
			new Tile(3259, 3438), new Tile(3258, 3438), new Tile(3257, 3438),
			new Tile(3256, 3438), new Tile(3255, 3439), new Tile(3254, 3439),
			new Tile(3253, 3439), new Tile(3252, 3439), new Tile(3251, 3439),
			new Tile(3250, 3440), new Tile(3249, 3441), new Tile(3249, 3442),
			new Tile(3248, 3443), new Tile(3247, 3444), new Tile(3247, 3445),
			new Tile(3247, 3446), new Tile(3247, 3447), new Tile(3247, 3448),
			new Tile(3247, 3449), new Tile(3247, 3450), new Tile(3247, 3451),
			new Tile(3247, 3452), new Tile(3247, 3453), new Tile(3247, 3454),
			new Tile(3247, 3455), new Tile(3247, 3456), new Tile(3246, 3457),
			new Tile(3245, 3458), new Tile(3245, 3459), new Tile(3245, 3460),
			new Tile(3245, 3461), new Tile(3245, 3462), new Tile(3245, 3463),
			new Tile(3245, 3464), new Tile(3245, 3465), new Tile(3245, 3466),
			new Tile(3245, 3467), new Tile(3245, 3468), new Tile(3245, 3469),
			new Tile(3246, 3470), new Tile(3247, 3470), new Tile(3248, 3470),
			new Tile(3249, 3470), new Tile(3250, 3470), new Tile(3251, 3470),
			new Tile(3252, 3470), new Tile(3253, 3470), new Tile(3254, 3470),
			new Tile(3255, 3470), new Tile(3256, 3470), new Tile(3257, 3470),
			new Tile(3258, 3470), new Tile(3259, 3470), new Tile(3260, 3470),
			new Tile(3261, 3470), new Tile(3261, 3471), new Tile(3261, 3472),
			new Tile(3261, 3473), new Tile(3261, 3474), new Tile(3261, 3475),
			new Tile(3261, 3476), new Tile(3261, 3477), new Tile(3261, 3478),
			new Tile(3261, 3479), new Tile(3261, 3480), new Tile(3261, 3481),
			new Tile(3261, 3482), new Tile(3261, 3483), new Tile(3261, 3484),
			new Tile(3261, 3485), new Tile(3261, 3486), new Tile(3261, 3487),
			new Tile(3261, 3488), new Tile(3260, 3489), new Tile(3259, 3489),
			new Tile(3258, 3489), new Tile(3257, 3489), new Tile(3256, 3489),
			new Tile(3255, 3489), new Tile(3254, 3489), new Tile(3253, 3489),
			new Tile(3252, 3489), new Tile(3251, 3489), new Tile(3251, 3488),
			new Tile(3250, 3487), new Tile(3249, 3486), new Tile(3248, 3485),
			new Tile(3247, 3485), new Tile(3246, 3485), new Tile(3245, 3484),
			new Tile(3244, 3483), new Tile(3244, 3482), new Tile(3243, 3482),
			new Tile(3242, 3482), new Tile(3241, 3481), new Tile(3240, 3481),
			new Tile(3239, 3480), new Tile(3238, 3479), new Tile(3237, 3478),
			new Tile(3236, 3477), new Tile(3236, 3476), new Tile(3236, 3475),
			new Tile(3236, 3474), new Tile(3236, 3473), new Tile(3236, 3472),
			new Tile(3236, 3471), new Tile(3236, 3470), new Tile(3236, 3469),
			new Tile(3236, 3468), new Tile(3235, 3467), new Tile(3234, 3467),
			new Tile(3233, 3467), new Tile(3233, 3466), new Tile(3234, 3465),
			new Tile(3235, 3464), new Tile(3236, 3464), new Tile(3236, 3463),
			new Tile(3236, 3462), new Tile(3236, 3461), new Tile(3236, 3460),
			new Tile(3236, 3459), new Tile(3236, 3458), new Tile(3236, 3457),
			new Tile(3236, 3456), new Tile(3236, 3455), new Tile(3235, 3454),
			new Tile(3235, 3453), new Tile(3235, 3452), new Tile(3235, 3451),
			new Tile(3234, 3450), new Tile(3233, 3449), new Tile(3232, 3448),
			new Tile(3231, 3447), new Tile(3230, 3447), new Tile(3229, 3446),
			new Tile(3228, 3445), new Tile(3227, 3444), new Tile(3226, 3443),
			new Tile(3225, 3442), new Tile(3224, 3441), new Tile(3223, 3440),
			new Tile(3222, 3439), new Tile(3221, 3438), new Tile(3220, 3438),
			new Tile(3219, 3438), new Tile(3218, 3437), new Tile(3218, 3436),
			new Tile(3218, 3435), new Tile(3217, 3435), new Tile(3216, 3435),
			new Tile(3215, 3435), new Tile(3214, 3435), new Tile(3213, 3435),
			new Tile(3212, 3435), new Tile(3211, 3435), new Tile(3210, 3435),
			new Tile(3209, 3436), new Tile(3208, 3436), new Tile(3207, 3437),
			new Tile(3206, 3438), new Tile(3206, 3439) };
	public Area Varrock = new Area(varTiles);
	
	@Override
	public void execute() {
		
		

	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "RoL'ed Out, terminating";
	}

	@Override
	public boolean isValid() {
		if (Varrock.contains(Players.getLocal().getLocation()))
			return true;
		return false;
	}

}
