package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;

import java.util.Arrays;
import java.util.function.Function;

public enum CellSignProvider2 implements CellSignProvidable {

    EMPTY(CellSnapshotStatus.EMPTY, cell -> "■"),
    FLAG(CellSnapshotStatus.FLAG, cell -> "⚑"),
    LAND_MINE(CellSnapshotStatus.LAND_MINE, cell -> "☼"),
    NUMBER(CellSnapshotStatus.NUMBER, cell -> String.valueOf(cell.getNearbyLandMineCount())),
    UNCHECKED(CellSnapshotStatus.UNCHECKED, cell -> "□"),
    ;

    private final CellSnapshotStatus status;
    private final Function<CellSnapshot, String> signProvider;

    CellSignProvider2(CellSnapshotStatus status, Function<CellSnapshot, String> signProvider) {
        this.status = status;
        this.signProvider = signProvider;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(status);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return signProvider.apply(cellSnapshot);
    }

    public static String findCellSignFrom(CellSnapshot snapshot) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(snapshot))
                .findFirst()
                .map(provider -> provider.provide(snapshot))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }

}
