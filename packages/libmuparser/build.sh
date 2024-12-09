TERMUX_PKG_HOMEPAGE="https://beltoforion.de/en/muparser"
TERMUX_PKG_DESCRIPTION="An extensible high performance math expression parser library written in C++"
TERMUX_PKG_GROUPS="science"
TERMUX_PKG_LICENSE="BSD 2-Clause"
TERMUX_PKG_MAINTAINER="@termux"
TERMUX_PKG_VERSION="2.3.4"
TERMUX_PKG_REVISION=1
TERMUX_PKG_SRCURL="https://github.com/beltoforion/muparser/archive/refs/tags/v$TERMUX_PKG_VERSION.tar.gz"
TERMUX_PKG_SHA256=0c3fa54a3ebf36dda0ed3e7cd5451c964afbb15102bdbcba08aafb359a290121
TERMUX_PKG_DEPENDS="libc++"
TERMUX_PKG_BUILD_IN_SRC=true
TERMUX_PKG_AUTO_UPDATE=true

termux_step_pre_configure() {
	LDFLAGS+=" -fopenmp -static-openmp"
}
