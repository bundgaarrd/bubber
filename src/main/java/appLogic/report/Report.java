// s245072 Lea
package appLogic.report;

import java.util.Set;
import appLogic.Summary;

public record Report(double hoursUsed, Set<Summary> activitySummaries, double expectedRemainingHours) {}