# If you use this bbclass, please ensure that SRCREV is set correctly.
# The version of the generated pakcage will be like:
# c<num_of_commits>.<commit_hash>-r<rev_num>

PV = "git${SRCPV}"
PKGV = "c${@'${GITPKGV}'.replace('+', '.')}"

inherit gitpkgv
